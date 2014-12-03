package it.synclab.patred.persistence;

import java.io.Serializable;

import javax.management.relation.Role;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NamedQueries({ @NamedQuery(name = "getAllUser", query = "select u from User u"),
		@NamedQuery(name = "getByUsernameUser", query = "select u from User u where u.username = :username"), @NamedQuery(name = "deleteAllUser", query = "delete from User u") })
@Entity
@Table
public class User implements Serializable {
	private static final long serialVersionUID = -3341290174467662162L;
	
	private String username;
	private String name;
	private String surname;
	private String password;
	private Role role;
	private Employee employee;
	private Manager manager;
	
	public User() {
		
	}
	
	@Id
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Manager getManager() {
		return manager;
	}
	
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", name=" + name + ", surname=" + surname + ", password=" + password + ", role=" + role + "]";
	}
	
}
