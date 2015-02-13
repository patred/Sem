package it.synclab.patred.sem.persistence.entities;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "TimesheetDetails")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "getAllTimesheetDetail", query = "select t from TimesheetDetail t"),
		@NamedQuery(name = "getTimesheetDetail", query = "select t from TimesheetDetail t where t.id = :id"),
		@NamedQuery(name = "deleteAllTimesheetDetail", query = "delete from TimesheetDetail t") })

public class TimesheetDetail implements Serializable {
	
	private static final long serialVersionUID = -8658021501556200946L;

	private Date day;
	private Time in;
	private Time out;
	private double hours;
	private double picap;
	private boolean rep;
	private Note note;
	private String trasf;
	private Code code;
	private String commessa;
}
