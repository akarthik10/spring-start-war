package dao;

import models.Post;
import models.PostRequest;
import models.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
public class PostRequestDAOImpl implements PostRequestDAO{
    @Autowired
    SessionFactory sessionFactory;

    public PostRequestDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public PostRequestDAOImpl() {
    }

    @Override
    @Transactional
    public PostRequest get(Long id) {
        PostRequest post = (PostRequest) sessionFactory.getCurrentSession().get(PostRequest.class, id);
        return post;
    }

    @Override
    @Transactional
    public void saveOrUpdate(PostRequest post) throws Exception {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(post);
        currentSession.flush();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        sessionFactory.getCurrentSession().delete(get(id));
    }


    @Override
    public List<PostRequest> getByUser(User user) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(PostRequest.class);
        c.add(Restrictions.eq("user", user));
        return c.list();
    }
}
