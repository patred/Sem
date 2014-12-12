package it.synclab.patred.sem.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Employees")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "getAllEmployee", query = "select u from Employee u"), @NamedQuery(name = "deleteAllEmployee", query = "delete from Employee u") })
public class Employee implements Serializable {
	
	private static final long serialVersionUID = -1378102048195683176L;
	
	private Long id;
	private String role;
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
	
	public Long getCalendarId() {
		return calendarId;
	}
	
	public void setCalendarId(Long calendarId) {
		this.calendarId = calendarId;
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