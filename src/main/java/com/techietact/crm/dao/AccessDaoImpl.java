package com.techietact.crm.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.techietact.crm.entity.Access;

@Repository
public class AccessDaoImpl implements AccessDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Access> getAllAccess() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Access where deleted = false", Access.class).getResultList();
    }

    @Override
    public void saveAccess(Access access) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(access);
    }

    @Override
    public Access getAccess(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Access.class, id);
    }

    @Override
    public void deleteAccess(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<?> query = session.createQuery("delete from Access where accessId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Access> findByIds(List<Integer> accessIds) {
        Session session = sessionFactory.getCurrentSession();
        Query<Access> query = session.createQuery("from Access where accessId in (:ids)", Access.class);
        query.setParameterList("ids", accessIds);
        return query.getResultList();
    }

    @Override
    public Access findByAccessName(String accessName) {
        Session session = sessionFactory.getCurrentSession();
        Query<Access> query = session.createQuery("from Access where accessName = :name", Access.class);
        query.setParameter("name", accessName);
        return query.uniqueResult();
    }
    
    @Override
    public List<Access> findAllByIds(List<Integer> ids) {
        Session session = sessionFactory.getCurrentSession();
        Query<Access> query = session.createQuery("from Access where accessId in (:ids)", Access.class);
        query.setParameterList("ids", ids);
        return query.getResultList();
    }
    
    public List<Access> getAccessByIds(List<Integer> accessIds) {
		 Session session = sessionFactory.getCurrentSession();
	        return session.createQuery("from Access where accessId in (:ids)", Access.class)
	                      .setParameterList("ids", accessIds)
	                      .getResultList();
	 }
    
    @Override
    public List<Access> searchAccess(String keyword) {
        Session session = sessionFactory.getCurrentSession();
        Query<Access> query = session.createQuery(
            "from Access where lower(accessName) like :kw and deleted = false", Access.class);
        query.setParameter("kw", "%" + keyword.toLowerCase() + "%");
        return query.getResultList();
    }

    @Override
    public void softDelete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<?> query = session.createQuery("update Access set deleted = true where accessId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
    
    @Override
    public List<Access> getAccessByLoginId(int loginId) {
        Session session = sessionFactory.getCurrentSession();

        String hql = "select distinct a " +
                     "from Access a " +
                     "join a.privilleges p " +
                     "join p.role r " +
                     "join r.users u " +
                     "where u.userId = :loginId";

        Query<Access> query = session.createQuery(hql, Access.class);
        query.setParameter("loginId", loginId);

        return query.getResultList();
    }

}
