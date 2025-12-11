package com.techietact.crm.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name="privillege")
@SQLDelete(sql = "UPDATE Privillege SET is_deleted = true WHERE privillegeId = ?")
@Where(clause = "is_deleted = false")
public class Privillege extends BaseEntity{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int privillegeId;
	
	@Column(name = "is_deleted", nullable = false)
	private boolean isDeleted = false;
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name="privillege_name",nullable = false, unique=true)
	private String privillegeName ;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "privilege_access",
			joinColumns = @JoinColumn(name = "privillege_id"),
			inverseJoinColumns = @JoinColumn(name = "access_id")
		)
	private List<Access> access = new ArrayList<>();
	
	@Transient
    private List<Integer> accessIds;
	
	public Privillege() {}
	

	public int getPrivillegeId() {
		return privillegeId;
	}

	public void setPrivillegeId(int privillegeId) {
		this.privillegeId = privillegeId;
	}

	public String getPrivillegeName() {
		return privillegeName;
	}

	public void setPrivillegeName(String privillegeName) {
		this.privillegeName = privillegeName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Access> getAccess() {
		return access;
	}

	public void setAccess(List<Access> access) {
		this.access = access;
	}
	
	public List<Integer> getAccessIds() {
        return accessIds;
    }

    public void setAccessIds(List<Integer> accessIds) {
        this.accessIds = accessIds;
    }
}
