package br.com.shoppingcart.dao;

import java.util.Collection;

public interface DAOInterface<T> {

    void create(T create);

    void patchUpdate(T patch);

    void putUpdate(T put);

    void remove(T remove);

    Collection<T> findAll();

    T findById(Long id);

}
