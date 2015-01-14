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
@Table(name = "Clients")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "getAllClient", query = "select c from Client c"), @NamedQuery(name = "getClient", query = "select c from Client c where c.id = :id"),
		@NamedQuery(name = "deleteAllClient", query = "delete from Client c") })
public class Client implements Serializable {
	
	private static final long serialVersionUID = 2939416427160823796L;
	
	private Long id;
	private String companyName;
	private String registeredOffice;
	private String address;
	private String telephone;
	private String description;
	
	public Client() {
	}
	
	public Client(String companyName) {
		this.companyName = companyName;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getRegisteredOffice() {
		return registeredOffice;
	}
	
	public void setRegisteredOffice(String registeredOffice) {
		this.registeredOffice = registeredOffice;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
		Client other = (Client) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Client [id=" + id + ", companyName=" + companyName + ", registeredOffice=" + registeredOffice + ", address=" + address + ", telephone=" + telephone
				+ ", description=" + description + "]";
	}
	
}
