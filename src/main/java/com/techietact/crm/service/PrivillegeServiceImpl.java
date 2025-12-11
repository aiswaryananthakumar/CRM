package com.techietact.crm.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techietact.crm.dao.AccessDao;
import com.techietact.crm.dao.PrivillegeDao;
import com.techietact.crm.entity.Access;
import com.techietact.crm.entity.Privillege;

@Service
public class PrivillegeServiceImpl implements PrivillegeService {

    @Autowired
    private PrivillegeDao privillegeDao;
    
    @Autowired
    private AccessDao accessDao;

    @Override
    @Transactional
    public List<Privillege> getAllPrivilleges() {
        return privillegeDao.getAllPrivilleges();
    }

    @Override
    @Transactional
    public void savePrivillege(Privillege privilege) {
        List<Access> managedAccessList = accessDao.findAllByIds(
                privilege.getAccess().stream().map(Access::getAccessId).toList()
        );
        privilege.setAccess(managedAccessList);
        privillegeDao.savePrivillege(privilege);
    }

    @Override
    @Transactional
    public Privillege getPrivillege(int id) {
        return privillegeDao.getPrivillege(id);
    }

    @Override
    @Transactional
    public void deletePrivillege(int id) {
        privillegeDao.deletePrivillege(id);
    }

    @Override
    @Transactional
    public Privillege findByName(String privillegeName) {
        return privillegeDao.findByName(privillegeName);
    }

    @Override
    @Transactional
    public List<Privillege> findByIds(List<Integer> ids) {
        return privillegeDao.findByIds(ids);
    }
    
    @Override
    @Transactional
    public List<Access> getAccessByIds(List<Integer> ids) {
        return accessDao.findAllByIds(ids);
    }
    
    @Override
    @Transactional
    public List<Privillege> searchByName(String keyword) {
        return privillegeDao.searchByName(keyword);
    }
    
    @Override
    @Transactional
    public List<Privillege> getPrivilegesByLoginId(int loginId) {
        return privillegeDao.getPrivilegesByLoginId(loginId);
    }


}
