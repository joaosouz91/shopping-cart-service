package br.com.shoppingcart.connection;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("carrinhojpa");

    public static EntityManagerFactory getEmf() {
        return emf;
    }
}
