package dao;

import models.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by akarthik10 on 11/14/2015.
 */
@Repository
public class UserDAOImpl implements UserDAO{
    @Autowired
    SessionFactory sessionFactory;

    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserDAOImpl() {
    }

    @Override
    @Transactional
    public User get(Long id) {
        User user = (User) sessionFactory.getCurrentSession().get(User.class, id);
        return user;
    }

    @Override
    @Transactional
    public void saveOrUpdate(User user) throws Exception {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(user);
        currentSession.flush();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        sessionFactory.getCurrentSession().delete(get(id));
    }

    @Transactional
    public List getUserByEmail(String email) {
        email = email.toLowerCase();
        Criteria c = sessionFactory.getCurrentSession().createCriteria(User.class);
        c.add(Restrictions.eq("email", email));
        return c.list();
    }
}
