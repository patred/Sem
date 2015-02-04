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
@Table(name = "Timesheets")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "getAllTimesheet", query = "select t from Timesheet t"),
	@NamedQuery(name = "getTimesheet", query = "select t from Timesheet t where t.id = :id"),
	@NamedQuery(name = "deleteAllTimesheet", query = "delete from Timesheet t")
	})
public class Timesheet implements Serializable {
	
	private static final long serialVersionUID = 2985597129988855345L;
	
	private Long id;
	private TSStatus status;
	
	public Timesheet() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
