package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
//import sun.jvm.hotspot.debugger.Address;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {


    private SessionFactory sessionFactory;

    @Autowired
    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    @Transactional
    public User getUserByCar(Car car) {
        final String HQL = "FROM User user JOIN FETCH user.car WHERE user.car.model=:Model AND user.car.series=:Series";
        return sessionFactory.getCurrentSession()
                .createQuery(HQL, User.class)
                .setParameter("Model", car.getModel())
                .setParameter("Series", car.getSeries())
                .uniqueResult();
    }

}
