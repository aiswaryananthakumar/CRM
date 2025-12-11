package com.techietact.crm.service;

import java.util.List;

import com.techietact.crm.entity.Access;
import com.techietact.crm.entity.Privillege;

public interface PrivillegeService {
    List<Privillege> getAllPrivilleges();
    void savePrivillege(Privillege privilege);
    Privillege getPrivillege(int id);
    void deletePrivillege(int id);
    Privillege findByName(String privillegeName);
    List<Privillege> findByIds(List<Integer> ids);
    List<Access> getAccessByIds(List<Integer> ids);
    List<Privillege> searchByName(String keyword);
    List<Privillege> getPrivilegesByLoginId(int loginId);

}
