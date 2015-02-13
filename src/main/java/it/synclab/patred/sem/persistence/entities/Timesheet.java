package it.synclab.patred.sem.persistence.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Timesheets")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "getAllTimesheet", query = "select t from Timesheet t"),
		@NamedQuery(name = "getTimesheet", query = "select t from Timesheet t where t.id = :id"),
		@NamedQuery(name = "getByEmployeeTimesheet", query = "select t from Timesheet t where t.employee = :employee"),
		@NamedQuery(name = "deleteAllTimesheet", query = "delete from Timesheet t") })

public class Timesheet implements Serializable {
	
	private static final long serialVersionUID = 6497639764636768375L;

	private Long id;
	private TSStatus status;
	private int month;
	private int year;
	private String note;
	private Employee employee;
	
	public Timesheet() {
		this.status = TSStatus.Draft;
		this.month = Calendar.JANUARY;
		this.year = 1970;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public TSStatus getStatus() {
		return status;
	}
	
	public void setStatus(TSStatus status) {
		this.status = status;
	}
	
	public int getMonth() {
		return month;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	@ManyToOne(optional = false)
/*	@JoinColumns({ @JoinColumn(name = "product_depname", referencedColumnName = "application_department_name"),
			@JoinColumn(name = "product_appid", referencedColumnName = "application_appid"),
			@JoinColumn(name = "product_typo", referencedColumnName = "typo"),
			@JoinColumn(name = "product_prodid", referencedColumnName = "prodid") })*/
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public void update(Timesheet timesheet){
		this.month = timesheet.getMonth();
		this.year = timesheet.getYear();
		this.note = timesheet.getNote();
		this.status = timesheet.getStatus();
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
		Timesheet other = (Timesheet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Timesheet [id=" + id + ", status=" + status + ", month=" + month + ", year=" + year + ", note=" + note + ", employee=" + employee + "]";
	}
	
}
