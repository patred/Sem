package it.synclab.patred.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@NamedQueries({ @NamedQuery(name = "getAllManager", query = "select m from Manager m"), @NamedQuery(name = "deleteAllManager", query = "delete from Manager m") })
@Entity
@Table
public class Manager implements Serializable {
	
	private static final long serialVersionUID = -6981475672787964705L;
	
	private Integer ManagerId;
	private User user;
	private String role;
	
	@Id
	public Integer getManagerId() {
		return ManagerId;
	}
	
	public void setManagerId(Integer managerId) {
		ManagerId = managerId;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ManagerId == null) ? 0 : ManagerId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Manager other = (Manager) obj;
		if (ManagerId == null) {
			if (other.ManagerId != null)
				return false;
		} else if (!ManagerId.equals(other.ManagerId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Manager [ManagerId=" + ManagerId + ", user=" + user + ", role=" + role + "]";
	}
	
}
