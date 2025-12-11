package com.techietact.crm.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.techietact.crm.entity.Lead;

/**
 * Created by CryptoSingh1337 on 6/1/2021
 *
 * It is a DAO implementation of {@link LeadDAO}
 */

@Repository
public class LeadDAOImpl implements LeadDAO {

    private SessionFactory factory;

    @Override
    public void addLead(Lead lead) {
        Session session = factory.getCurrentSession();
        session.saveOrUpdate(lead);
    }

    @Override
    public Lead getLead(int id) {
        Session session = factory.getCurrentSession();
        return session.get(Lead.class, id);
    }

    @Override
    public List<Lead> getLeads(int sortBy) {
        String sort;
        switch (sortBy) {
            case 0: sort = "firstName";
                break;
            case 2: sort = "email";
                break;
            default:
                sort = "lastName";
        }
        Session session = factory.getCurrentSession();
        String queryString = "from Lead where deleted = false order by " + sort;
        Query<Lead> query = session.createQuery(queryString, Lead.class);
        return query.getResultList();
    }


    @Override
    public void deleteLead(int id) {
        Session session = factory.getCurrentSession(); // get hibernate session.
        Query query = session.createQuery("delete from Lead where id=:leadID");
        query.setParameter("leadID", id);
        query.executeUpdate(); // delete the lead from the database.
    }

    @Override
    public List<Lead> searchLead(String searchString) {
        Session session = factory.getCurrentSession();
        Query<Lead> query = session.createQuery(
            "from Lead where deleted = false and (firstName like :search or lastName like :search or email like :search)",
            Lead.class
        );
        searchString = "%" + searchString + "%";
        query.setParameter("search", searchString);
        return query.getResultList();
    }


    @Autowired
    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }
    
    @Override
    public Lead findByLeadName(String searchString) {
        Session session = factory.getCurrentSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery<Lead> cq=cb.createQuery(Lead.class);
        Root<Lead> root=cq.from(Lead.class);
        cq.select(root);
        cq.where(cb.equal(root.get("firstName"), searchString));
        Lead leads=session.createQuery(cq).uniqueResult();
        return leads;
    }
    
    @Override
    public void softDeleteLead(int id) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("update Lead set deleted = true where id = :leadID");
        query.setParameter("leadID", id);
        query.executeUpdate();
    } 
    
    @Override
    public List<Lead> getLeadsByUser(int sortBy, int createdBy) {
        String sort;
        switch (sortBy) {
            case 0: sort = "firstName"; break;
            case 2: sort = "email"; break;
            default: sort = "lastName";
        }
        Session session = factory.getCurrentSession();
        String queryString = "from Lead where deleted = false and createdby = :createdBy order by " + sort;
        Query<Lead> query = session.createQuery(queryString, Lead.class);
        query.setParameter("createdBy", createdBy);
        return query.getResultList();
    }

}
