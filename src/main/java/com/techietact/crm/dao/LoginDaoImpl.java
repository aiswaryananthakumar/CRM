
  package com.techietact.crm.dao;
  
  import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session; import org.hibernate.SessionFactory; import
  org.hibernate.query.Query; import
  org.springframework.beans.factory.annotation.Autowired; import
  org.springframework.stereotype.Repository;
  
  import com.techietact.crm.entity.Login;
  
  @Repository public class LoginDaoImpl implements LoginDao {
  
  @Autowired SessionFactory sessionFactory;
  
  @Override public Login login(Login login) { 
	  Session session=sessionFactory.openSession(); 
  Login loginRes=null;
  Query<Login> sql=session.createQuery("from Login where userName=:userNameInput",Login.class);
	
  sql.setParameter("userNameInput", login.getUserName()); List<Login>
  response=sql.getResultList(); if(null!=response &&response.size()>0) {
  loginRes =sql.getResultList().get(0); } return loginRes; 
 
  }
  
  
  @Override 
  public Login findByUserName(String userName) { 
      Session session = sessionFactory.getCurrentSession();
      Query<Login> sql = session.createQuery("from Login where userName=:userNameInput", Login.class);
      sql.setParameter("userNameInput", userName);
      List<Login> response = sql.getResultList();
      return (response != null && !response.isEmpty()) ? response.get(0) : null;
  }
  
  @Override
  @Transactional
  public void save(Login login) {
      Session session = sessionFactory.openSession();
      session.save(login);
      session.close();
  }

  
  
  }
  