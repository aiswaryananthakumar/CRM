package com.techietact.crm.service;

import java.util.List;

import com.techietact.crm.entity.Lead;

/**
 * Created by CryptoSingh1337 on 6/1/2021
 */

public interface LeadService {

    void addLead(Lead lead);
    Lead getLead(int id);
    List<Lead> getLeads(int sortBy);
    void deleteLead(int id);
    List<Lead> searchLead(String searchString);
    void softDeleteLead(int id);
    List<Lead> getLeadsByUser(int sortBy, int createdBy);


}
