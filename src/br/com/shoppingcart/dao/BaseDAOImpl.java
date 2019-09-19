package br.com.shoppingcart.dao;

import br.com.shoppingcart.connection.ConnectionFactory;
import br.com.shoppingcart.entity.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

public class BaseDAOImpl<T extends BaseEntity> implements DAOInterface<T> {

    public BaseDAOImpl(){}

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
    public void remove(T t) {
        EntityManager em = ConnectionFactory.getEmf().createEntityManager();
        T tt = (T) em.find(getPersistentClass(), t.getId());
        em.getTransaction().begin();
        em.remove(tt);
        em.getTransaction().commit();
        em.close();
    }


    @Override
    public Collection<T> findAll() {
        EntityManager em = ConnectionFactory.getEmf().createEntityManager();
        em.getTransaction().begin();
        String clazz = getPersistentClass().getSimpleName();
        Query query = em.createQuery("Select t from " + clazz + " t");
        Collection<T> list = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return list;
    }

    @Override
    public T findById(Long id) {
        EntityManager em = ConnectionFactory.getEmf().createEntityManager();
        em.getTransaction().begin();
        T t = (T) em.find(getPersistentClass(), id);
        em.getTransaction().commit();
        em.close();
        return t;
    }

    public Class<?> getPersistentClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<?>) type.getActualTypeArguments()[0];
    }
}
