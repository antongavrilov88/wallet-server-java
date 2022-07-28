package DAO;

import model.Token;
import model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

public class UserDAO {

    public User findByEmail(String email) {

        Query<User> query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from User x where x.email=:email", User.class);
        query.setParameter("email", email);
        return query.uniqueResult();
    }


    public User findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
    }


    public Serializable save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Serializable userCreated = session.save(user);
        tx1.commit();
        session.close();
        return userCreated;
    }

    public Serializable save(Token token) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Serializable tokenCreated = session.save(token);
        tx1.commit();
        session.close();
        return tokenCreated;
    }

    public void delete(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }


    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }
}
