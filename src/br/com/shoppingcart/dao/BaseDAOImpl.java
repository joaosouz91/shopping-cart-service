package br.com.shoppingcart.dao;

import br.com.shoppingcart.connection.ConnectionFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;

public class BaseDAOImpl<T> implements DAOInterface<T> {

    private Class<T> persistentClass;

    @Override
    public void create(T t) {
        EntityManager em = ConnectionFactory.getEmf().createEntityManager();
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void patchUpdate(T t) {

    }

    @Override
    public void putUpdate(T t) {
        EntityManager em = ConnectionFactory.getEmf().createEntityManager();
        em.getTransaction().begin();
        em.merge(t);
        em.getTransaction().commit();
        em.close();
    }


    @Override
    public Collection<T> findAll() {
        EntityManager em = ConnectionFactory.getEmf().createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("Select t.type from " + persistentClass.getSimpleName() +" t");
        Collection<T> list = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return list;
    }

    @Override
    public T findById(Long id) {
        EntityManager em = ConnectionFactory.getEmf().createEntityManager();
        em.getTransaction().begin();
        T t = em.find(persistentClass, id);
        em.getTransaction().commit();
        em.close();
        return t;
    }
}
