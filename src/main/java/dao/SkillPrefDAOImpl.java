package dao;

import models.Category;
import models.SkillPref;
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
public class SkillPrefDAOImpl implements SkillPrefDAO {
    @Autowired
    SessionFactory sessionFactory;

    public SkillPrefDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SkillPrefDAOImpl() {
    }

    @Override
    @Transactional
    public SkillPref get(String id) {
        SkillPref skillPref = (SkillPref) sessionFactory.getCurrentSession().get(SkillPref.class, id);
        return skillPref;
    }

    @Override
    @Transactional
    public void saveOrUpdate(SkillPref skillPref) throws Exception {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(skillPref);
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
        return sessionFactory.getCurrentSession().createCriteria(SkillPref.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

}