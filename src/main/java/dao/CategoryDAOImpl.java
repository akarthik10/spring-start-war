package dao;

import models.Category;
import models.Post;
import models.User;
import org.hibernate.Criteria;
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
public class CategoryDAOImpl implements CategoryDAO {
    @Autowired
    SessionFactory sessionFactory;

    public CategoryDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public CategoryDAOImpl() {
    }

    @Override
    @Transactional
    public Category get(String id) {
        Category category = (Category) sessionFactory.getCurrentSession().get(Category.class, id);
        return category;
    }

    @Override
    @Transactional
    public void saveOrUpdate(Category category) throws Exception {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(category);
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
        return sessionFactory.getCurrentSession().createCriteria(Category.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

}