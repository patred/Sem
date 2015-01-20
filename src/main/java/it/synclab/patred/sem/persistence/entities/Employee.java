package it.synclab.patred.sem.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Employees")
@XmlRootElement
@NamedQueries(
		{ 	@NamedQuery(name = "getAllEmployee", query = "select e from Employee e"),
			@NamedQuery(name = "getEmployee", query = "select e from Employee e where e.id = :id"),
			@NamedQuery(name = "getByUserEmployee", query = "select e from Employee e where e.user = :user"),
			@NamedQuery(name = "getAllUsernamesEmployee", query = "select e.user.username from Employee e where e.user.username is not null"),
			@NamedQuery(name = "deleteAllEmployee", query = "delete from Employee e") 
		})
public class Employee implements Serializable {
	
	private static final long serialVersionUID = -1378102048195683176L;
	
	private Long id;
	private String name;
	private String surname;
	private String role;
	private User user;
	private Long calendarId;
	
	public Employee() {
	}
	
	public Employee(String role) {
		this.role = role;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	@OneToOne
	@JoinColumn(unique = true)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getCalendarId() {
		return calendarId;
	}
	
	public void setCalendarId(Long calendarId) {
		this.calendarId = calendarId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Employee other = (Employee) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", role=" + role + ", calendarId=" + calendarId + "]";
	}
	
}
