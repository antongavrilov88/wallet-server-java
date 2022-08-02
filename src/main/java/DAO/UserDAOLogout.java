package DAO;

import model.Token;
import model.User;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

public class UserDAOLogout extends UserDAO {

    public Token getToken(String userToken) {
        Query<Token> query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from Token x where x.userToken=:userToken", Token.class);
        query.setParameter("userToken", userToken);
        return query.uniqueResult();
    }
}
