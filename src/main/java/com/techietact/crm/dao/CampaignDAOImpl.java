package com.techietact.crm.dao;

import com.techietact.crm.entity.Campaign;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CampaignDAOImpl implements CampaignDAO {

    private SessionFactory factory;

    @Override
    public void addCampaign(Campaign campaign) {
        Session session = factory.getCurrentSession();
        session.saveOrUpdate(campaign); 
    }

    @Override
    public Campaign getCampaign(int id) {
        Session session = factory.getCurrentSession();
        return session.get(Campaign.class, id); 
    }

    @Override
    public List<Campaign> getCampaigns(int sortBy) {
        String sort;
        switch (sortBy) {
            case 1: 
            	sort = "name";
            	break;
            case 2: 
            	sort = "startDate";
            	break;
            default: 
            	sort = "endDate";
        }
        Session session = factory.getCurrentSession();
        String queryString = "from Campaign where deleted = false order by " + sort;
        Query<Campaign> query = session.createQuery(queryString, Campaign.class);
        return query.getResultList();
    }


    @Override
    public void deleteCampaign(int id) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("delete from Campaign where id=:campaignID");
        query.setParameter("campaignID", id); 
        query.executeUpdate(); 
    }

    @Override
    public List<Campaign> searchCampaign(String searchString) {
        Session session = factory.getCurrentSession();
        Query<Campaign> query = session.createQuery(
            "from Campaign where deleted = false and (name like :search or description like :search)",
            Campaign.class
        );
        searchString = "%" + searchString + "%";
        query.setParameter("search", searchString);
        return query.getResultList();
    }


    @Override
    public Campaign findByCampaignName(String searchString) {
        Session session = factory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();  //construct criteria query
        CriteriaQuery<Campaign> cq = cb.createQuery(Campaign.class);
        Root<Campaign> root = cq.from(Campaign.class);
        cq.select(root);
        cq.where(cb.equal(root.get("name"), searchString)); 
        return session.createQuery(cq).uniqueResult(); 
    }

    @Autowired
    public void setFactory(SessionFactory factory) {
        this.factory = factory; // inject SessionFactory to interact with the database
    }
    
    @Override
    public void softDeleteCampaign(int id) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("update Campaign set deleted = true where id = :campaignID");
        query.setParameter("campaignID", id);
        query.executeUpdate();
    }
    
    @Override
    public List<Campaign> getCampaignsByUser(int sortBy, int loginId) {
        String sort;
        switch (sortBy) {
            case 1: sort = "name"; break;
            case 2: sort = "startDate"; break;
            default: sort = "endDate";
        }

        Session session = factory.getCurrentSession();
        String queryString = "from Campaign where deleted = false and createdby = :loginId order by " + sort;
        Query<Campaign> query = session.createQuery(queryString, Campaign.class);
        query.setParameter("loginId", loginId);
        return query.getResultList();
    }


}
