package it.synclab.patred.sem.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "EmployeeOrders")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "getAllEmployeeOrder", query = "select e from EmployeeOrder e"),
	@NamedQuery(name = "getByEmployeeClient", query = "select e from EmployeeOrder e where e.pk.employee = :employee"),
	@NamedQuery(name = "getByOrderClient", query = "select e from EmployeeOrder e where e.pk.order = :order"),
	@NamedQuery(name = "deleteAllEmployeeOrder", query = "delete from EmployeeOrder e")
	})
public class EmployeeOrder implements Serializable {
	
	private static final long serialVersionUID = -3142644870040445153L;
	
	private EmployeeOrderPk pk;
	
	public EmployeeOrder() {
		pk = new EmployeeOrderPk();
	}
	
	@Id
	public EmployeeOrderPk getPk() {
		return pk;
	}
	
	public void setPk(EmployeeOrderPk pk) {
		this.pk = pk;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
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
		EmployeeOrder other = (EmployeeOrder) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "EmployeeOrder [pk=" + pk + "]";
	}
	
}
