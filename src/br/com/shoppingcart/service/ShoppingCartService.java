package br.com.shoppingcart.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.omg.CORBA.portable.ApplicationException;

import com.google.gson.Gson;

import br.com.shoppingcart.dto.CartDTO;
import br.com.shoppingcart.dto.CartListDTO;
import br.com.shoppingcart.entity.Cart;
import br.com.shoppingcart.entity.ItemCart;
import br.com.shoppingcart.entity.Product;

public class ShoppingCartService {

	private static List<Cart> cartList = Collections.synchronizedList(new ArrayList<Cart>());
	
	public ShoppingCartService() {
		
		Product p = new Product("1", "teste", new BigDecimal("14.78"));
		ItemCart ic = new ItemCart(p.getCode(), new BigDecimal("16.90"), 3);
		Cart c = new Cart("1");
		c.addItem(ic);
		cartList.add(c);
	}

	public CartListDTO getAllCarts() {

		if (!cartList.isEmpty()) {
			return new CartListDTO(cartList);
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
	 * @param String
	 * @return CartDTO
	 * @throws ApplicationException
	 */

	public CartDTO create(String json) throws ApplicationException {

		try {
			Cart cart = new Gson().fromJson(json, Cart.class);
			if (cart != null) {
				for (Cart c : cartList) {
					if (c.getClientCode().equals(cart.getClientCode())) {
						return new CartDTO(cart, 409);
					}
				}
				cartList.add(cart);
				return new CartDTO(cart, 201);
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
	public BigDecimal getValorTicketMedio() {

		BigDecimal valorTotalCarrinhos = BigDecimal.ZERO;
		BigDecimal ticketMedio = BigDecimal.ZERO;

		for (Cart carrinhoCompras : cartList) {
			valorTotalCarrinhos = valorTotalCarrinhos.add(carrinhoCompras.getTotalValue());
		}

		ticketMedio = valorTotalCarrinhos.divide(new BigDecimal(cartList.size()));

		return ticketMedio;
	}

	/**
	 * Invalida um carrinho de compras quando o cliente faz um checkout ou sua
	 * sessão expirar. Deve ser efetuada a remoção do carrinho do cliente passado
	 * como parâmetro da listagem de carrinhos de compras.
	 *
	 * @param identificacaoCliente
	 * @return Retorna um boolean, tendo o valor true caso o cliente passado como
	 *         parämetro tenha um carrinho de compras e e false caso o cliente não
	 *         possua um carrinho.
	 */
	public boolean ivalidate(String identificacaoCliente) {
		for (Cart cart : cartList) {
			if (cart.getClientCode().equals(identificacaoCliente)) {
				cartList.remove(cart);
				return true;
			}
		}
		return false;
	}

}
