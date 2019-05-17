package br.com.shoppingcartservice.entity;

import java.math.BigDecimal;

public class ItemCart {
	
	private String code;
	private BigDecimal unitaryValue;
	private long quantity;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public BigDecimal getUnitaryValue() {
		return unitaryValue;
	}
	
	public void setUnitaryValue(BigDecimal unitaryValue) {
		this.unitaryValue = unitaryValue;
	}
	
	public long getQuantity() {
		return quantity;
	}
	
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

}
