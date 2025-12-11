package com.techietact.crm.dao;

import java.util.List;

import com.techietact.crm.entity.Lead;

public interface LeadDAO {

    void addLead(Lead lead);

    Lead getLead(int id);

    List<Lead> getLeads(int sortBy);

    void deleteLead(int id);

    List<Lead> searchLead(String searchString);

	Lead findByLeadName(String searchString);
	
	void softDeleteLead(int id);
	
	List<Lead> getLeadsByUser(int sortBy, int createdBy);


}
