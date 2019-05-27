package br.com.shoppingcart.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.shoppingcart.entity.Cart;

public class CartListDTO {
	
	private List<Cart> cartList = new ArrayList<Cart>();

	public List<Cart> getCartList() {
		return cartList;
	}

	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}

	                                          

}
