package dao;

import models.Category;
import models.Post;
import models.Subcategory;
import org.hibernate.Query;
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
public class SubcategoryDAOImpl implements SubcategoryDAO {
    @Autowired
    SessionFactory sessionFactory;

    public SubcategoryDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SubcategoryDAOImpl() {
    }

    @Override
    @Transactional
    public Subcategory get(String id) {
        Subcategory subcategory = (Subcategory) sessionFactory.getCurrentSession().get(Subcategory.class, id);
        return subcategory;
    }

    @Override
    @Transactional
    public void saveOrUpdate(Subcategory subcategory) throws Exception {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(subcategory);
        currentSession.flush();
    }

    @Override
    @Transactional
    public void delete(String id) {
        sessionFactory.getCurrentSession().delete(get(id));
    }

}