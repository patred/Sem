package it.synclab.patred.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "getAllUser", query = "select u from User u"),
		@NamedQuery(name = "getByUsernameUser", query = "select u from User u where u.username = :username"), @NamedQuery(name = "deleteAllUser", query = "delete from User u") })
public class User implements Serializable {
	private static final long serialVersionUID = -3341290174467662162L;
	
	private String username;
	private String name;
	private String surname;
	private String password;
	private Roles role;
	private Employee employee;
	private Manager manager;
	
	public User() {
		this.role = Roles.Empty;
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.role = Roles.Empty;
	}
	
	public User(String username, String password, Roles role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	@Id
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@OneToOne
	@JoinColumn(unique = true)
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@OneToOne
	@JoinColumn(unique = true)
	public Manager getManager() {
		return manager;
	}
	
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	public Roles getRole() {
		return role;
	}
	
	public void setRole(Roles role) {
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
		return "User [username=" + username + ", name=" + name + ", surname=" + surname + ", password=" + password + ", role=" + role + ", employee=" + employee + ", manager="
				+ manager + "]";
	}
	
}