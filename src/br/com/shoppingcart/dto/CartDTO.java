package br.com.shoppingcart.dto;

import br.com.shoppingcart.entity.Cart;

public class CartDTO {
	
	private Cart cart;
	private Integer statusCode;
	
	public CartDTO() {}
	
	public CartDTO(Cart cart, Integer statusCode) {
		this.cart = cart;
		this.statusCode = statusCode;
	}

	public Cart getCart() {
		return cart;
	}
	
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	public Integer getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

}
