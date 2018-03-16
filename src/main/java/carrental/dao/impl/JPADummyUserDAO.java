package carrental.dao.impl;

import carrental.dao.interf.IGenericDAO;
import carrental.domain.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


public class JPADummyUserDAO implements IGenericDAO<User> {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public User update(User updateUser) {
        em.merge(updateUser);
        return updateUser;
    }

    @Transactional
    @Override
    public User get(String name){
        List<User> user =  em.createQuery("from User u where u.name = :name", User.class)
                             .setParameter("name",name)
                             .getResultList();
        return user.isEmpty() ? null : user.get(0);
    }

    @Transactional
    @Override
    public User create(User object){
        em.persist(object);
        return object;
    }

    @Transactional
    @Override
    public User delete(User object){
        em.remove(em.merge(object));
        return object;
    }

    @Override
    public List<User> list(){
        return em.createQuery("from User", User.class)
                 .getResultList();
    }
}
