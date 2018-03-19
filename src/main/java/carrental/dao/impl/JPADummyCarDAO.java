package carrental.dao.impl;

import carrental.dao.interf.IGenericDAO;
import carrental.domain.Car;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class JPADummyCarDAO implements IGenericDAO<Car> {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Car update(Car updateCar){
        em.merge(updateCar);
        return updateCar;
    }

    @Override
    public Car get(String name){
        List<Car> car =  em.createQuery("from Car u where u.licensePlateNumber = :name", Car.class)
                .setParameter("name",name)
                .getResultList();
        return car.isEmpty() ? null : car.get(0);
    }


    @Transactional
    @Override
    public Car create(Car object){
        em.persist(object);
        return object;
    }


    @Transactional
    @Override
    public Car delete(Car object){
        em.remove(em.merge(object));
        return object;
    }

    @Override
    public List<Car> list(){
        return em.createQuery("from Car", Car.class)
                .getResultList();
    }
}
