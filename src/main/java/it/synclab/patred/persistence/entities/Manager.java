package it.synclab.patred.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Managers")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "getAllManager", query = "select m from Manager m"), @NamedQuery(name = "deleteAllManager", query = "delete from Manager m") })
public class Manager implements Serializable {
	
	private static final long serialVersionUID = -6981475672787964705L;
	
	private Long id;
	private String role;
	
	public Manager() {
	}
	
	public Manager(String role) {
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
		Manager other = (Manager) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Manager [id=" + id + ", role=" + role + "]";
	}
	
}
