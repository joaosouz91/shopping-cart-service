package br.com.shoppingcart.service;

import br.com.shoppingcart.dao.BaseDAOImpl;
import br.com.shoppingcart.dao.CartDAO;
import br.com.shoppingcart.dto.CartDTO;
import br.com.shoppingcart.dto.CartListDTO;
import br.com.shoppingcart.entity.Cart;
import br.com.shoppingcart.entity.ItemCart;
import br.com.shoppingcart.enums.OperationStatus;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.omg.CORBA.portable.ApplicationException;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Caret;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingCartService {

	private static List<Cart> cartList = Collections.synchronizedList(new ArrayList<Cart>());
	
	public ShoppingCartService() {}

	/**
	 * Retorna a lista de carrinhos ativos
	 * @return
	 */
	public CartListDTO getAllCarts() {

		if (!cartList.isEmpty()) {
			return new CartListDTO(cartList);
		}
		return null;
	}
	
	
	/**
	 * Retorna o carrinho do cliente passado como par�metro
	 * @param clientCode
	 * @return
	 */
	public CartDTO getCart(String clientCode) {
		BaseDAOImpl<Cart> dao = new BaseDAOImpl<Cart>();
		if (!cartList.isEmpty()) {
			for(Cart c : cartList) {
				if(c.getClientCode().equals(clientCode)) {
					return new CartDTO(c, 200);
				}
			}
		}
		return null;
	}

	/**
	 * Cria e retorna um novo carrinho de compras para o cliente passado como
	 * parâmetro.
	 *
	 * Caso já exista um carrinho de compras para o cliente passado como parâmetro,
	 * este carrinho deverá ser retornado.
	 *
	 * @param json
	 * @return CartDTO
	 * @throws ApplicationException
	 */

	public CartDTO createCart(String json) throws ApplicationException {

		CartDAO dao = new CartDAO();
		try {
			Cart jsonCart = new Gson().fromJson(json, Cart.class);
			if (jsonCart != null) {

				if(jsonCart.getId() != null){

				}
				Cart dataBaseCart = dao.findByClientCode(jsonCart.getClientCode());
				if(dataBaseCart == null) {
					dao.create(jsonCart);
					return new CartDTO(jsonCart, 201);
				}
				return new CartDTO(dataBaseCart, 409);
			}
		} catch (Exception e) {
			throw new ApplicationException(json, null);
		}
		return null;
	}

	/**
	 * Retorna o valor do ticket médio no momento da chamada ao método. O valor do
	 * ticket médio é a soma do valor total de todos os carrinhos de compra dividido
	 * pela quantidade de carrinhos de compra. O valor retornado deverá ser
	 * arredondado com duas casas decimais, seguindo a regra: 0-4 deve ser
	 * arredondado para baixo e 5-9 deve ser arredondado para cima.
	 *
	 * @return BigDecimal
	 */
	public BigDecimal getTicketAverageValue() {

		BigDecimal valorTotalCarrinhos = BigDecimal.ZERO;
		BigDecimal ticketMedio = BigDecimal.ZERO;

		for (Cart carrinhoCompras : cartList) {
			valorTotalCarrinhos = valorTotalCarrinhos.add(carrinhoCompras.getTotalValue());
		}
		if(!valorTotalCarrinhos.equals(BigDecimal.ZERO)) {
			ticketMedio = valorTotalCarrinhos.divide(new BigDecimal(cartList.size()));
		}
		return ticketMedio;
	}

	/**
	 * Remove um carrinho de compras quando o cliente faz um checkout ou sua
	 * sessão expirar. Deve ser efetuada a remoção do carrinho do cliente passado
	 * como parâmetro da listagem de carrinhos de compras.
	 *
	 * @param identificacaoCliente
	 * @return Retorna um boolean, tendo o valor true caso o cliente passado como
	 *         parâmetro tenha um carrinho de compras e e false caso o cliente não
	 *         possua um carrinho.
	 */
	public boolean removeCart(String identificacaoCliente) {
		for (Cart cart : cartList) {
			if (cart.getClientCode().equals(identificacaoCliente)) {
				cartList.remove(cart);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adiciona um item ao carrinho
	 * @throws Exception 
	 */
	public CartDTO addItemCart(String clientCode, String json) {
		
		try {
			for(Cart cart : cartList) {
				if(cart.getClientCode().equals(clientCode)){
					
					ItemCart itemCart = new Gson().fromJson(json, ItemCart.class);
					String opStatus = cart.addItem(itemCart);
					
					if(opStatus != null && opStatus.equals(OperationStatus.CREATED.toString())) {
						return new CartDTO(cart, HttpServletResponse.SC_CREATED);
					}
					else if(opStatus != null && opStatus.equals(OperationStatus.MODIFIED.toString())) {
						return new CartDTO(cart, HttpServletResponse.SC_OK);
					}
				}
			}
		} catch (JsonSyntaxException _jse) {
			
		} catch (Exception e) {
			throw e;
		}
		return null;
	}
	
	/**
	 * Remove um item do carrinho
	 */
	public CartDTO removeItemCart(String clientCode, Long itemCartId) {
		
		for(Cart cart: cartList) {
			if(cart.getClientCode().equals(clientCode)) {
				
				String opStatus = cart.removeItem(itemCartId);
				
				if(opStatus != null && opStatus.equals(OperationStatus.MODIFIED.toString())) {
					return new CartDTO(cart, HttpServletResponse.SC_OK);
				}
			}
		}
		return null;
	}
	

}
