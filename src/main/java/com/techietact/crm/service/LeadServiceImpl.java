package com.techietact.crm.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techietact.crm.dao.LeadDAO;
import com.techietact.crm.entity.Lead;

/**
 * Created by CryptoSingh1337 on 6/1/2021
 */

@Service
public class LeadServiceImpl implements LeadService {

    private LeadDAO leadDAO;

    @Override
    @Transactional
    public void addLead(Lead lead) {
            leadDAO.addLead(lead);
    }

    @Transactional
    public Lead getLead(int id) {
        return leadDAO.getLead(id);
    }

    @Override
    @Transactional
    public List<Lead> getLeads(int sortBy) {
        return leadDAO.getLeads(sortBy);
    }

    @Override
    @Transactional
    public void deleteLead(int id) {
        leadDAO.deleteLead(id);
    }

    @Override
    @Transactional
    public List<Lead> searchLead(String searchString) {
        return leadDAO.searchLead(searchString);
    }

    @Autowired
    public void setLeadDAO(LeadDAO leadDAO) {
        this.leadDAO = leadDAO;
    }
    
    @Override
    @Transactional
    public void softDeleteLead(int id) {
        leadDAO.softDeleteLead(id);
    }
    
    @Override
    @Transactional
    public List<Lead> getLeadsByUser(int sortBy, int createdBy) {
        return leadDAO.getLeadsByUser(sortBy, createdBy);
    }


}
