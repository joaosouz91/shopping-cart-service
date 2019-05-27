package br.com.shoppingcart.entity;

import java.math.BigDecimal;

public class Product {
	
	private String code;
	private String description;
	private BigDecimal value;
	
	public Product(String code, String description, BigDecimal value) {
		this.code = code;
		this.description = description;
		this.value = value;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	
}
