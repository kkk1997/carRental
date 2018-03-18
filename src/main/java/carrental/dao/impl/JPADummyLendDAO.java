package carrental.dao.impl;

import carrental.dao.interf.IGenericDAO;
import carrental.domain.Lend;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class JPADummyLendDAO implements IGenericDAO<Lend> {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Lend update(Lend updateLend) {
        em.merge(updateLend);
        return updateLend;
    }

    @Override
    public Lend get(String name){
        List<Lend> lend =  em.createQuery("from Lend u where u.user.name = :name", Lend.class)
                .setParameter("name",name)
                .getResultList();
        return lend.isEmpty() ? null : lend.get(0);
    }

    @Transactional
    @Override
    public Lend create(Lend object){
        em.persist(object);
        return object;
    }

    @Transactional
    @Override
    public Lend delete(Lend object){
        em.remove(em.merge(object));
        return object;
    }

    @Override
    public List<Lend> list(){
        return em.createQuery("from Lend", Lend.class)
                .getResultList();
    }

}
