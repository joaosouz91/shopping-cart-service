package br.com.shoppingcart.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

import br.com.shoppingcart.dto.ItemCartDTO;
import br.com.shoppingcart.enums.OperationStatus;

@XmlRootElement
public class Cart {

	private String clientCode;
	private List<ItemCart> itemsList = Collections.synchronizedList(new ArrayList<ItemCart>());

	public Cart(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	/**
	 * Permite a adi��o de um novo item no carrinho de compras.
	 *
	 * Caso o item j� exista no carrinho para este mesmo produto: 
	 * A quantidade do item ser� a soma da quantidade atual com a quantidade passada como par�metro.
	 * Se o valor unit�rio informado for diferente do valor unit�rio atual do item, o novo
	 * valor unit�rio do item ser� o passado como par�metro.
	 *
	 * @param ItemCart
	 */
	public String addItem(ItemCart itemCart) {

		if (itemCart != null && itemCart.getCode() != null) {
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
	
	public Collection<ItemCart> getItemList() {
		return this.itemsList;
	}
	
	public BigDecimal getTotalValue() {
		return BigDecimal.ZERO;
	}

	public boolean removeItem() {
		return false;
	}

}
