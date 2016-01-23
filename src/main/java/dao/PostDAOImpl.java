package dao;

import models.Post;
import models.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by akarthik10 on 11/14/2015.
 */
@Repository
public class PostDAOImpl implements PostDAO{
    @Autowired
    SessionFactory sessionFactory;

    public PostDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public PostDAOImpl() {
    }

    @Override
    @Transactional
    public Post get(Long id) {
        Post post = (Post) sessionFactory.getCurrentSession().get(Post.class, id);
        return post;
    }

    @Override
    @Transactional
    public void saveOrUpdate(Post post) throws Exception {
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
    @Transactional
    public List<Post> search(double latitude, double longitude, String qr) {

        String query = "select *, (6371*2*ASIN(SQRT(POWER(SIN((:lat-abs(latitude))*pi()/180/2),2)+COS(:lat*pi()/180) * COS(abs(latitude)* pi()/180) *POWER(SIN((:lon -longitude)* pi()/180/2),2)))) as distance from POST p, CATEGORY c, SUBCATEGORY s WHERE p.category_ID = c.ID and p.subcategory_ID = s.ID and (title like :qr OR description like :qr OR s.name like :qr OR c.name like :qr) having distance < :dist ORDER BY distance";
        Session currentSession = sessionFactory.getCurrentSession();
        Query q = currentSession.createSQLQuery(query).addEntity(Post.class).setParameter("lat", latitude).setParameter("lon", longitude).setParameter("dist", 10).setParameter("qr", qr);
        return  (List<Post>) q.list();
    }

    @Override
    @Transactional
    public boolean hasRequestBy(User user, Post post) {
        String query = "select u.id from post_postrequest ppr, postrequest pr, users u where ppr.POST_ID = :pid and ppr.PostRequests_ID = pr.ID and pr.byUser_ID = :uid";
        Session currentSession = sessionFactory.getCurrentSession();
        Query q = currentSession.createSQLQuery(query).setParameter("pid", post.getId()).setParameter("uid", user.getId());
        return  q.list().size() > 0;
    }

    @Override
    @Transactional
    public List<Post> getPostByUser(User user) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Post.class);
        c.add(Restrictions.eq("user", user));
        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return c.list();
    }

}
