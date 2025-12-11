package com.techietact.crm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.techietact.crm.entity.Customer;
import com.techietact.crm.entity.Privillege;

/**
 * Created by CryptoSingh1337 on 6/1/2021
 * 
 * It is a DAO implementation of {@link CustomerDAO}
 */

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	@PersistenceContext
    private EntityManager entityManager;

	@Autowired
    private SessionFactory factory;
	
	@Autowired
    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void addCustomer(Customer customer) {
        Session session = factory.getCurrentSession();
        session.saveOrUpdate(customer);
    }

    @Override
    public Customer getCustomer(int id) {
        Session session = factory.getCurrentSession();
        return session.get(Customer.class, id);
    }

    @Override
    public List<Customer> getCustomers(int sortBy) {
        String sort;
        switch (sortBy) {
            case 0: sort = "firstName"; break;
            case 2: sort = "email"; break;
            default: sort = "lastName";
        }

        Session session = factory.getCurrentSession();
        // ✅ FIXED: include records where isDeleted is false or null
        String queryString = "from Customer where (isDeleted = false or isDeleted is null) order by " + sort;
        Query<Customer> query = session.createQuery(queryString, Customer.class);
        return query.getResultList();
    }
    
    @Override
    public void deleteCustomer(int id) {
        Customer customer = getCustomer(id); // fetch the object
        if(customer != null) {
            factory.getCurrentSession().delete(customer);
        }
    }

    @Override
    public List<Customer> searchCustomer(String searchString) {
        Session session = factory.getCurrentSession();
        Query<Customer> query = session.createQuery(
            "from Customer where (firstName like :search or lastName like :search or email like :search) " +
            "and (isDeleted = false or isDeleted is null)",
            Customer.class
        );
        searchString = "%" + searchString + "%";
        query.setParameter("search", searchString);
        return query.getResultList();
    }
    
    //select * from customer where customerName='selva' and emailId='selva@gamil.com';
    @Override
    public List<Customer> findByCustomerName(String searchString) {
      //Step:1
    	Session session = factory.getCurrentSession();
     //Step:2
        CriteriaBuilder cb=session.getCriteriaBuilder();
      //Step:3  
        CriteriaQuery<Customer> cq=cb.createQuery(Customer.class);
       //Step 4
        Root<Customer> root=cq.from(Customer.class);
        cq.select(root);
       //step 5       
        Predicate p1 = cb.like(root.get("firstName"), "%" + searchString + "%");
        Predicate p2 = cb.like(root.get("email"), "%" + searchString + "%");
        Predicate notDeleted = cb.or(
                cb.isFalse(root.get("isDeleted")),
                cb.isNull(root.get("isDeleted"))
            );
        cq.select(root).where(cb.and(cb.or(p1, p2), notDeleted))
        .orderBy(cb.asc(root.get("firstName")));
       
        //Step :6 
        return session.createQuery(cq).list();
    }
    
    @Override
    public long getCustomerCount() {
    	
    	//Step:1
    	Session session = factory.getCurrentSession();
     //Step:2
        CriteriaBuilder cb=session.getCriteriaBuilder();
    	CriteriaQuery<Long> cq = cb.createQuery(Long.class);
    	Root<Customer> root = cq.from(Customer.class);
    	cq.select(cb.count(root));
    	Query<Long> query = session.createQuery(cq);
    	List<Long> cutomerSize = query.getResultList();
    	return cutomerSize.get(0);	
    }
    
    @Override
    public void softDeleteCustomer(int id) {
        Customer customer = getCustomer(id);
        if (customer != null) {
            customer.setIsDeleted(true);
            factory.getCurrentSession().saveOrUpdate(customer);
        }
    }
    
    @Override
    public List<Customer> getCustomersByCreator(int createdBy, int sortBy) {
        String sort;
        switch (sortBy) {
            case 0: sort = "firstName"; break;
            case 2: sort = "email"; break;
            default: sort = "lastName";
        }

        Session session = factory.getCurrentSession();

        String hql = "FROM Customer WHERE (isDeleted = false OR isDeleted IS NULL) " +
                     "AND createdby = :createdBy ORDER BY " + sort;

        Query<Customer> query = session.createQuery(hql, Customer.class);
        query.setParameter("createdBy", createdBy);

        return query.getResultList();
    }



}
