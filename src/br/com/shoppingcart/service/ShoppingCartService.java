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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingCartService {

	private static List<Cart> cartList2 = Collections.synchronizedList(new ArrayList<Cart>());
	
	public ShoppingCartService() {}

	/**
	 * Retorna a lista de carrinhos ativos
	 * @return
	 */
	public CartListDTO getAllCarts() {

		CartDAO dao = new CartDAO();
		List<Cart> cartList = new ArrayList<Cart>(dao.findAll());

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
		CartDAO dao = new CartDAO();
		Cart dataBaseCart = dao.findByClientCode(clientCode);
		if (dataBaseCart != null) {
			return new CartDTO(dataBaseCart, 200);
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

		for (Cart carrinhoCompras : cartList2) {
			valorTotalCarrinhos = valorTotalCarrinhos.add(carrinhoCompras.getTotalValue());
		}
		if(!valorTotalCarrinhos.equals(BigDecimal.ZERO)) {
			ticketMedio = valorTotalCarrinhos.divide(new BigDecimal(cartList2.size()));
		}
		return ticketMedio;
	}

	/**
	 * Remove um carrinho de compras quando o cliente faz um checkout ou sua
	 * sessão expirar. Deve ser efetuada a remoção do carrinho do cliente passado
	 * como parâmetro da listagem de carrinhos de compras.
	 *
	 * @param clientCode
	 * @return Retorna um boolean, tendo o valor true caso o cliente passado como
	 *         parâmetro tenha um carrinho de compras e e false caso o cliente não
	 *         possua um carrinho.
	 */
	public boolean removeCart(String clientCode) {

		CartDAO dao = new CartDAO();
		Cart dataBaseCart = dao.findByClientCode(clientCode);
		if(dataBaseCart !=null){
			dao.remove(dataBaseCart);
			return true;
		}
		return false;
	}
	
	/**
	 * Adiciona um item ao carrinho
	 * @throws Exception 
	 */
	public CartDTO addItemCart(String clientCode, String json) {

		try {
			CartDAO dao = new CartDAO();
			Cart dataBaseCart = dao.findByClientCode(clientCode);

			if(dataBaseCart == null){
				return null;
			}

			BaseDAOImpl<ItemCart> itemCartBaseDAO = new BaseDAOImpl<ItemCart>();
			ItemCart itemCartFromJson = new Gson().fromJson(json, ItemCart.class);

			ItemCart itemCart = itemCartBaseDAO.findById(itemCartFromJson.getId());

			if(itemCart != null){
				itemCartBaseDAO.putUpdate(itemCartFromJson);
				return new CartDTO(dataBaseCart, HttpServletResponse.SC_OK);
			} else {
				itemCartBaseDAO.create(itemCartFromJson);
				return new CartDTO(dataBaseCart, HttpServletResponse.SC_CREATED);
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
		
		for(Cart cart: cartList2) {
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
