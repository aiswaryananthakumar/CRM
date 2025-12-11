package com.techietact.crm.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.techietact.crm.dao.AccessDao;
import com.techietact.crm.entity.Access;

@Service
public class AccessServiceImpl implements AccessService {

    @Autowired
    private AccessDao accessDao;

    @Override
    @Transactional
    public List<Access> getAllAccess() {
        return accessDao.getAllAccess();
    }

    @Override
    @Transactional
    public void saveAccess(Access access) {
        accessDao.saveAccess(access);
    }

    @Override
    @Transactional
    public Access getAccess(int id) {
        return accessDao.getAccess(id);
    }

    @Override
    @Transactional
    public void deleteAccess(int id) {
        accessDao.deleteAccess(id);
    }

    @Override
    @Transactional
    public List<Access> findByIds(List<Integer> accessIds) {
        return accessDao.findByIds(accessIds);
    }

    @Override
    @Transactional
    public Access findByAccessName(String accessName) {
        return accessDao.findByAccessName(accessName);
    }
    
    @Override
    @Transactional
    public List<Access> findAllByIds(List<Integer> ids) {
        return accessDao.findAllByIds(ids);
    }
    
    @Override
	@Transactional
	public List<Access> getAccessByIds(List<Integer> accessId) {
		// TODO Auto-generated method stub
		 return accessDao.getAccessByIds(accessId);
	}
    
    @Override
    @Transactional
    public List<Access> searchAccess(String keyword) {
        return accessDao.searchAccess(keyword);
    }

    @Override
    @Transactional
    public void softDelete(int id) {
        accessDao.softDelete(id);
    }

    @Override
    @Transactional
    public List<Access> getAccessByLoginId(int loginId) {
        return accessDao.getAccessByLoginId(loginId);
    }

}
