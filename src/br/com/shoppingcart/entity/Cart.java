package br.com.shoppingcart.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.ApplicationException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.shoppingcart.dto.ItemCartDTO;

public class Cart {

	private String clientCode;
	private static List<ItemCart> itemsList = Collections.synchronizedList(new ArrayList<ItemCart>());

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
	 * Caso o item j� exista no carrinho para este mesmo produto, as seguintes
	 * regras dever�o ser seguidas: - A quantidade do item dever� ser a soma da
	 * quantidade atual com a quantidade passada como par�metro. -- OK - Se o valor
	 * unit�rio informado for diferente do valor unit�rio atual do item, o novo
	 * valor unit�rio do item dever� ser o passado como par�metro. --OK
	 *
	 * Devem ser lan�adas subclasses de RuntimeException caso n�o seja poss�vel
	 * adicionar o item ao carrinho de compras.
	 *
	 * @param String jsonObject
	 */
	public ItemCartDTO addItem(String json) {

		try {
			ItemCart itemCart = new Gson().fromJson(json, ItemCart.class);

			if (itemCart != null && itemCart.getCode() != null) {

				for (ItemCart i : itemsList) {

					if (i.equals(itemCart)) {

						i.setQuantity(i.getQuantity() + itemCart.getQuantity());

						if (i.getUnitaryValue() != itemCart.getUnitaryValue()) {
							i.setUnitaryValue(itemCart.getUnitaryValue());
						}
						return new ItemCartDTO(i, HttpServletResponse.SC_OK);
					}
				}

				itemsList.add(itemCart);
				return new ItemCartDTO(itemCart, HttpServletResponse.SC_CREATED);
			}

		} catch (IllegalArgumentException e) {
			System.out.println("=== N�o foi poss�vel adicionar o item ao carrinho ===");
		} catch (Exception e) {
			System.out.println("=== N�o foi poss�vel adicionar o item ao carrinho ===");
		}
		return null;
	}
	
	public BigDecimal getTotalValue() {
		return BigDecimal.ZERO;
	}

	public boolean removeItem() {
		return false;
	}

}
