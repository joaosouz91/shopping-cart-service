package br.com.shoppingcart.dto;

import br.com.shoppingcart.entity.ItemCart;

public class ItemCartDTO {
	
	private ItemCart itemCart;
	private Integer statusCode;
	
	public ItemCartDTO(ItemCart itemCart, Integer statusCode) {
		super();
		this.itemCart = itemCart;
		this.statusCode = statusCode;
	}
	
	public ItemCart getItemCart() {
		return itemCart;
	}
	
	public void setItemCart(ItemCart itemCart) {
		this.itemCart = itemCart;
	}
	
	public Integer getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	
}
