package br.com.shoppingcart.dao;

import br.com.shoppingcart.entity.BaseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface DAOInterface<T> {

    void create(T create);

    void patchUpdate(T patch);

    void putUpdate(T put);

    void remove(T remove);

    Collection<T> findAll();

    T findById(Long id);

}
