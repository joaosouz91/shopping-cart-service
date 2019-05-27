package br.com.shoppingcart.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.rmi.CORBA.Util;

import com.google.gson.Gson;

import br.com.shoppingcart.dto.CartDTO;
import br.com.shoppingcart.entity.Cart;

public class ShoppingCartService {
	
	private static List<Cart> cartList = Collections.synchronizedList(new ArrayList<Cart>());
	
	
	/**
     * Cria e retorna um novo carrinho de compras para o cliente passado como parâmetro.
     *
     * Caso já exista um carrinho de compras para o cliente passado como parâmetro, este carrinho deverá ser retornado.
     *
     * @param String
     * @return CartDTO
     */
	
	public CartDTO create(String json) {
		Cart cart = null;
		try{
			cart = new Gson().fromJson(json, Cart.class);
			if(cart != null) {
				for (Cart c : cartList) {
					if(c.getClientCode().equals(cart.getClientCode())) {
						return new CartDTO(cart, 409);
					}
				}
		    	cartList.add(cart);
		    	return new CartDTO(cart, 201);
			}
		}catch (Exception e) {
			
		}
		
		return new CartDTO(null, 400);
    }
	
	/**
     * Retorna o valor do ticket médio no momento da chamada ao método.
     * O valor do ticket médio é a soma do valor total de todos os carrinhos de compra dividido
     * pela quantidade de carrinhos de compra.
     * O valor retornado deverá ser arredondado com duas casas decimais, seguindo a regra:
     * 0-4 deve ser arredondado para baixo e 5-9 deve ser arredondado para cima.
     *
     * @return BigDecimal
     */
    public BigDecimal getValorTicketMedio() {
    	
    	BigDecimal valorTotalCarrinhos = BigDecimal.ZERO;
    	BigDecimal ticketMedio = BigDecimal.ZERO;
    	
    	for (Cart carrinhoCompras : cartList) {
    		valorTotalCarrinhos = valorTotalCarrinhos.add(carrinhoCompras.getTotalValue());
		}
    	
    	ticketMedio = valorTotalCarrinhos.divide(new BigDecimal(cartList.size()));
    	
    	return ticketMedio;
    }
    
    /**
     * Invalida um carrinho de compras quando o cliente faz um checkout ou sua sessão expirar.
     * Deve ser efetuada a remoção do carrinho do cliente passado como parâmetro da listagem de carrinhos de compras.
     *
     * @param identificacaoCliente
     * @return Retorna um boolean, tendo o valor true caso o cliente passado como parämetro tenha um carrinho de compras e
     * e false caso o cliente não possua um carrinho.
     */
    public boolean ivalidate(String identificacaoCliente) {
    	for (Cart carrinhoCompras : cartList) {
			if(carrinhoCompras.getClientCode().equals(identificacaoCliente)) {
				cartList.remove(carrinhoCompras);
				return true;
			}
		}
    	return false;
    }

}
