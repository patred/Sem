package it.synclab.patred.sem.persistence.entities;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

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
@Table(name = "TimesheetDetails")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "getAllTimesheetDetail", query = "select t from TimesheetDetail t"),
		@NamedQuery(name = "getTimesheetDetail", query = "select t from TimesheetDetail t where t.id = :id"),
		@NamedQuery(name = "deleteAllTimesheetDetail", query = "delete from TimesheetDetail t") })
public class TimesheetDetail implements Serializable {
	
	private static final long serialVersionUID = -2764439269938376123L;

	private Long id;
	private Date day;
	private Time ongoin;
	private Time ongoout;
	private double hours;
	private double picap;
	private boolean availability;
	private Note note;
	private String transfer;
	private Code code;
	private Long orderId;
	private Timesheet timesheet;
	
	public TimesheetDetail() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDay() {
		return day;
	}
	
	public void setDay(Date day) {
		this.day = day;
	}
	
	public Time getOngoin() {
		return ongoin;
	}
	
	public void setOngoin(Time ongoin) {
		this.ongoin = ongoin;
	}
	
	public Time getOngoout() {
		return ongoout;
	}
	
	public void setOngoOut(Time ongoout) {
		this.ongoout = ongoout;
	}
	
	public double getHours() {
		return hours;
	}
	
	public void setHours(double hours) {
		this.hours = hours;
	}
	
	public double getPicap() {
		return picap;
	}
	
	public void setPicap(double picap) {
		this.picap = picap;
	}
	
	public boolean isAvailability() {
		return availability;
	}
	
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	
	public Note getNote() {
		return note;
	}
	
	public void setNote(Note note) {
		this.note = note;
	}
	
	public String getTransfer() {
		return transfer;
	}
	
	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}
	
	public Code getCode() {
		return code;
	}
	
	public void setCode(Code code) {
		this.code = code;
	}
	
	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	@ManyToOne
	public Timesheet getTimesheet() {
		return timesheet;
	}
	
	public void setTimesheet(Timesheet timesheet) {
		this.timesheet = timesheet;
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
		TimesheetDetail other = (TimesheetDetail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "TimesheetDetail [id=" + id + ", day=" + day + ", ongoin=" + ongoin + ", ongoout=" + ongoout + ", hours=" + hours + ", picap=" + picap + ", availability="
				+ availability + ", note=" + note + ", transfer=" + transfer + ", code=" + code + ", orderId=" + orderId + ", timesheet=" + timesheet + "]";
	}
	
}
