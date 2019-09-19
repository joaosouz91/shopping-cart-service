package br.com.shoppingcart.entity;

import br.com.shoppingcart.enums.OperationStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
public class Cart implements BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String clientCode;

	@OneToMany(fetch = FetchType.EAGER)
	private Collection<ItemCart> itemsList;

	public Cart(){}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	/**
	 * Permite adicionar de um novo item no carrinho de compras.
	 *
	 * Caso o item j� exista no carrinho para este mesmo produto: 
	 * A quantidade do item ser� a soma da quantidade atual com a quantidade passada como par�metro.
	 * Se o valor unit�rio informado for diferente do valor unit�rio atual do item, o novo
	 * valor unit�rio do item ser� o passado como par�metro.
	 *
	 * @param itemCart
	 */
	public String addItem(ItemCart itemCart) {

		if (itemCart != null && itemCart.getId() != null) {
			for (ItemCart i : itemsList) {
				if (i.equals(itemCart)) {

					i.setQuantity(i.getQuantity() + itemCart.getQuantity());

					if (i.getUnitaryValue() != itemCart.getUnitaryValue()) {
						i.setUnitaryValue(itemCart.getUnitaryValue());
					}
					return OperationStatus.MODIFIED.toString();
				}
			}
			itemsList.add(itemCart);
			return OperationStatus.CREATED.toString();
		}
		return null;
	}

	public Long getId() {
		return id;
	}

	public Collection<ItemCart> getItemsList() {
		return itemsList;
	}

	public void setItemsList(Collection<ItemCart> itemsList) {
		this.itemsList = itemsList;
	}

	public BigDecimal getTotalValue() {
		
		BigDecimal totalValue = BigDecimal.ZERO;
		
		for (ItemCart itemCart : itemsList) {
			totalValue = totalValue.add(itemCart.getUnitaryValue());
		}
		return totalValue;
	}

	public String removeItem(Long id) {
		
		if (id != null) {
			for (ItemCart i : itemsList) {
				if (i.getId().equals(id)) {
					itemsList.remove(i);
					return OperationStatus.MODIFIED.toString();
				}
			}
		}
		return null;
	}

}
