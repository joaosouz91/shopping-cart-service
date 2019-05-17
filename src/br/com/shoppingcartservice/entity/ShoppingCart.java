package br.com.shoppingcartservice.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingCart {
	
	private String clientCode;
	List<ItemCart> itemsList = Collections.synchronizedList(new ArrayList<ItemCart>());
	
	public ShoppingCart(String clientCode) {
		this.clientCode = clientCode;
	}
	
	public String getClientCode() {
		return clientCode;
	}
	
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	
	public boolean addItem() {
		return false;
	}
	
	public boolean removeItem() {
		return false;
	}

}
