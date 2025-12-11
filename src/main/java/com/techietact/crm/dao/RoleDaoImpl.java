package com.techietact.crm.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.techietact.crm.entity.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Role> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Role", Role.class).getResultList();
    }

    @Override
    public Role findById(int id) {
        return sessionFactory.getCurrentSession().get(Role.class, id);
    }

    @Override
    public void save(Role role) {
        sessionFactory.getCurrentSession().saveOrUpdate(role);
    }

    @Override
    @Transactional
    public void softDelete(int id) {
        Role role = entityManager.find(Role.class, id);
        if (role != null) {
            role.setDeleted(true);
            entityManager.merge(role);
        }
    }

    @Override
    @Transactional
    public void restore(int id) {
        Role role = entityManager.find(Role.class, id);
        if (role != null) {
            role.setDeleted(false);
            entityManager.merge(role);
        }
    }

    @Override
    public Role findByName(String roleName) {
        Session session = sessionFactory.getCurrentSession();
        Query<Role> query = session.createQuery(
                "from Role where roleName = :name and isDeleted = false", Role.class);
        query.setParameter("name", roleName);
        return query.uniqueResult();
    }

    @Override
    @Transactional
    public List<Role> searchRoles(String roleName) {
        Session session = sessionFactory.getCurrentSession();
        Query<Role> query = session.createQuery(
                "from Role where lower(roleName) like :roleName and isDeleted = false", Role.class);
        query.setParameter("roleName", "%" + roleName.toLowerCase() + "%");
        return query.getResultList();
    }

    @Override
    public Role findByRoleName(String roleName) {
        try {
            return entityManager.createQuery(
                    "FROM Role r WHERE r.roleName = :roleName and r.isDeleted = false", Role.class)
                    .setParameter("roleName", roleName)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Role> getRoleByLoginId(int loginId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Role> query = session.createQuery(
            "from Role where createdby = :loginId", Role.class);
        query.setParameter("loginId", loginId);
        return query.getResultList();
    }


}
