package it.synclab.patred.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@NamedQueries({ @NamedQuery(name = "getAllEmployee", query = "select u from Employee u"), @NamedQuery(name = "deleteAllEmployee", query = "delete from Employee u") })
@Entity
@Table
public class Employee implements Serializable {
	
	private static final long serialVersionUID = -1378102048195683176L;
	
	private Integer EmployeeId;
	private User user;
	private String role;
	private Integer calendarId;
	
	public Employee() {
	}
	
	public Employee(String role) {
	}
	
	@Id
	@GeneratedValue
	public Integer getEmployeeId() {
		return EmployeeId;
	}
	
	public void setEmployeeId(Integer employeeId) {
		EmployeeId = employeeId;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Integer getCalendarId() {
		return calendarId;
	}
	
	public void setCalendarId(Integer calendarId) {
		this.calendarId = calendarId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((EmployeeId == null) ? 0 : EmployeeId.hashCode());
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
		if (EmployeeId == null) {
			if (other.EmployeeId != null)
				return false;
		} else if (!EmployeeId.equals(other.EmployeeId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Employee [EmployeeId=" + EmployeeId + ", user=" + user + ", role=" + role + ", calendarId=" + calendarId + "]";
	}
	
}
