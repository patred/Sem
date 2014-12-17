package it.synclab.patred.sem.persistence.entities;

import it.synclab.patred.sem.persistence.entities.converter.DateConverter;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@Table(name = "OrderDailys")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "getAllOrderDaily", query = "select o from OrderDaily o"), @NamedQuery(name = "deleteAllOrderDaily", query = "delete from OrderDaily o") })
public class OrderDaily implements Serializable {
	
	private static final long serialVersionUID = 2939416427160823796L;
	
	private Long id;
	private Employee employee;
	private Client client;
	private Manager manager;
	private Date orderDate;
	private int time;
	private String note;
	
	public OrderDaily() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@ManyToOne
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	@ManyToOne
	public Manager getManager() {
		return manager;
	}
	
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	@XmlJavaTypeAdapter(value = DateConverter.class)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getOrderDate() {
		return orderDate;
	}
	
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
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
		OrderDaily other = (OrderDaily) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "OrderDaily [id=" + id + ", employee=" + employee + ", client=" + client + ", manager=" + manager + ", orderDate=" + orderDate + ", time=" + time + ", note=" + note
				+ "]";
	}
	
}
