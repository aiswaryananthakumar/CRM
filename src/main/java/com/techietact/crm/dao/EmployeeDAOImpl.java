package com.techietact.crm.dao;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.techietact.crm.entity.Employee;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    private SessionFactory factory;

    @Autowired
    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    // ✅ Save or Update (fixes NonUniqueObjectException)
    @Override
    public void save(Employee employee) {
        Session session = factory.getCurrentSession();
        session.merge(employee);   // merge ensures Hibernate manages detached objects
    }

    // ✅ Optional separate update (but merge is safer than update)
    @Override
    public void update(Employee employee) {
        Session session = factory.getCurrentSession();
        session.merge(employee);   // use merge instead of update
    }

    @Override
    public Employee getEmployee(int id) {
        Session session = factory.getCurrentSession();
        return session.get(Employee.class, id);
    }

    @Override
    public List<Employee> getEmployees(int sortBy) {
        String sort;
        switch (sortBy) {
            case 0: sort = "firstName"; break;
            case 2: sort = "email"; break;
            default: sort = "lastName";
        }
        Session session = factory.getCurrentSession();
        String queryString = "from Employee order by " + sort;
        Query<Employee> query = session.createQuery(queryString, Employee.class);
        return query.getResultList();
    }

    @Override
    public void deleteEmployee(int id) {
        Session session = factory.getCurrentSession();
        Employee employee = session.get(Employee.class, id);
        if (employee != null) {
            session.delete(employee);
        }
    }

    @Override
    public List<Employee> searchEmployee(String searchString) {
        Session session = factory.getCurrentSession();
        Query<Employee> query = session.createQuery(
            "from Employee where firstName like :search or lastName like :search or email like :search",
            Employee.class
        );
        query.setParameter("search", "%" + searchString + "%");
        return query.getResultList();
    }

    @Override
    public List<Employee> findByEmployeeName(String searchString) {
        Session session = factory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> root = cq.from(Employee.class);

        Predicate p1 = cb.like(root.get("firstName"), "%" + searchString + "%");
        Predicate p2 = cb.like(root.get("email"), "%" + searchString + "%");
        cq.where(cb.or(p1, p2));
        cq.orderBy(cb.asc(root.get("firstName")));

        return session.createQuery(cq).list();
    }

    @Override
    public long getEmployeeCount() {
        Session session = factory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Employee> root = cq.from(Employee.class);
        cq.select(cb.count(root));
        return session.createQuery(cq).getSingleResult();
    }
    
    @Override
    public List<Employee> getEmployeesByCreator(int loginId, int sortBy) {
        String sort;
        switch (sortBy) {
            case 0: sort = "firstName"; break;
            case 2: sort = "email"; break;
            default: sort = "lastName";
        }

        Session session = factory.getCurrentSession();
        String hql = "from Employee where createdby = :creatorId order by " + sort;
        Query<Employee> query = session.createQuery(hql, Employee.class);
        query.setParameter("creatorId", loginId);

        return query.getResultList();
    }

}
