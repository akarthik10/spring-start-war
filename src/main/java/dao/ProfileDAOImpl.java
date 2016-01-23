package dao;

import models.Category;
import models.Profile;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by akarthik10 on 12/30/2015.
 */
@Repository
public class ProfileDAOImpl implements ProfileDAO {
    @Autowired
    SessionFactory sessionFactory;

    public ProfileDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public ProfileDAOImpl() {
    }

    @Override
    @Transactional
    public Profile get(String id) {
        Profile profile = (Profile) sessionFactory.getCurrentSession().get(Profile.class, id);
        return profile;
    }

    @Override
    @Transactional
    public void saveOrUpdate(Profile profile) throws Exception {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(profile);
        currentSession.flush();
    }

    @Override
    @Transactional
    public void delete(String id) {
        sessionFactory.getCurrentSession().delete(get(id));
    }

    @Override
    @Transactional
    public List getAll(){
        return sessionFactory.getCurrentSession().createCriteria(Profile.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

}