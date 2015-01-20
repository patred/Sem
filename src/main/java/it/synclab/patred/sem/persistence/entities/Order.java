package it.synclab.patred.sem.persistence.entities;

import it.synclab.patred.sem.util.CodeUtils;

import java.io.Serializable;

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
@Table(name = "Orders")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "getAllOrder", query = "select o from Order o"), @NamedQuery(name = "getOrder", query = "select o from Order o where o.id = :id"),
		@NamedQuery(name = "deleteAllOrder", query = "delete from Order u") })
public class Order implements Serializable {
	private static final long serialVersionUID = 3723514083069329181L;
	private static final String PATTERN = "@@@000";
	private Long id;
	private Client client;
	private String description;
	private String code;
	
	public Order() {
		this.code = CodeUtils.generaCode(PATTERN);
		
	}
	
	public Order(String description) {
		this.description = description;
		this.code = CodeUtils.generaCode(PATTERN);
	}
	
	public Order(String description, String code) {
		this.description = description;
		this.code = code;
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
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCode() {
		if(code == null)
			this.code = CodeUtils.generaCode(PATTERN);
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
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
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", client=" + client + ", description=" + description + ", code=" + code + "]";
	}
	
}
