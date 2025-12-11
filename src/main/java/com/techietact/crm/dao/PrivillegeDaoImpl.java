package com.techietact.crm.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.techietact.crm.entity.Privillege;

@Repository
public class PrivillegeDaoImpl implements PrivillegeDao {
	
	@Autowired
    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Privillege> getAllPrivilleges() {
        Session session = entityManager.unwrap(Session.class);
        Query<Privillege> query = session.createQuery(
            "SELECT DISTINCT p FROM Privillege p " +
            "LEFT JOIN FETCH p.role " +
            "LEFT JOIN FETCH p.access", Privillege.class
        );
        return query.getResultList();
    }

    @Override
    public void savePrivillege(Privillege privilege) {
        Session session = sessionFactory.getCurrentSession();
        if (privilege.getPrivillegeId() != 0) {
            session.merge(privilege);
        } else {
            session.save(privilege);
        }
    }

    @Override
    public Privillege getPrivillege(int id) {
        return sessionFactory.getCurrentSession().get(Privillege.class, id);
    }


    @Override
    public void deletePrivillege(int id) {
        Session session = entityManager.unwrap(Session.class);
        Privillege privillege = session.get(Privillege.class, id);
        if (privillege != null) {
            session.delete(privillege);
        }
    }

    @Override
    public Privillege findByName(String privillegeName) {
        Query<Privillege> query = entityManager.unwrap(Session.class).createQuery(
            "SELECT p FROM Privillege p " +
            "LEFT JOIN FETCH p.role " +
            "LEFT JOIN FETCH p.access " +
            "WHERE p.privillegeName = :name", Privillege.class
        );
        query.setParameter("name", privillegeName);
        List<Privillege> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<Privillege> findByIds(List<Integer> ids) {
        Query<Privillege> query = entityManager.unwrap(Session.class).createQuery(
            "SELECT DISTINCT p FROM Privillege p " +
            "LEFT JOIN FETCH p.role " +
            "LEFT JOIN FETCH p.access " +
            "WHERE p.privillegeId IN :ids", Privillege.class
        );
        query.setParameter("ids", ids);
        return query.getResultList();
    }
    
    @Override
    public List<Privillege> searchByName(String keyword) {
        Session session = entityManager.unwrap(Session.class);
        Query<Privillege> query = session.createQuery(
            "SELECT DISTINCT p FROM Privillege p " +
            "LEFT JOIN FETCH p.role " +
            "LEFT JOIN FETCH p.access " +
            "WHERE LOWER(p.privillegeName) LIKE :keyword", Privillege.class
        );
        query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
        return query.getResultList();
    }
    
    @Override
    public List<Privillege> getPrivilegesByLoginId(int loginId) {
        Session session = entityManager.unwrap(Session.class);
        Query<Privillege> query = session.createQuery(
            "SELECT DISTINCT p FROM Privillege p " +
            "LEFT JOIN FETCH p.role " +
            "LEFT JOIN FETCH p.access " +
            "WHERE p.createdby = :loginId", Privillege.class
        );
        query.setParameter("loginId", loginId);
        return query.getResultList();
    }


}
