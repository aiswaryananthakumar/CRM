package com.techietact.crm.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name="role")
@Where(clause = "is_deleted = false")
public class Role extends BaseEntity{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;
	
	@Column(name="role_name",unique=true, nullable=false)
	private String roleName ;
	
	@Column(name="is_deleted", nullable=false)
    private boolean isDeleted = false;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "role",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Privillege> privilleges;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<Login> users;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public boolean isDeleted() { 
		return isDeleted; 
		}
	
    public void setDeleted(boolean deleted) {
    	isDeleted = deleted; 
    	}

	public List<Privillege> getPrivilleges() {
		return privilleges;
	}

	public void setPrivilleges(List<Privillege> privilleges) {
		this.privilleges = privilleges;
	}
	
	public List<Login> getUsers() { 
		return users; 
		}
	
    public void setUsers(List<Login> users) {
    	this.users = users;
    	}
}
