package br.com.shoppingcart.dao;

import br.com.shoppingcart.connection.ConnectionFactory;
import br.com.shoppingcart.entity.Cart;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CartDAO extends BaseDAOImpl<Cart> {

    public CartDAO(){
        super();
    }

    public Cart findByClientCode(String clientCode) {
        EntityManager em = ConnectionFactory.getEmf().createEntityManager();
        Query query = em.createQuery("select c from Cart c where c.clientCode = :clientCode");
        query.setParameter("clientCode", clientCode);
        List<Cart> cartList = query.getResultList();
        em.close();
        if (cartList != null && cartList.size() != 0) {
            Cart cart = cartList.get(0);
            return cart;
        }
        return null;
    }
}
