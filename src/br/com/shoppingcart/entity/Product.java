package br.com.shoppingcart.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product implements BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private BigDecimal value;

	public Product(Long id, String description, BigDecimal value) {
		this.id = id;
		this.description = description;
		this.value = value;
	}

	public Product(){}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
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
