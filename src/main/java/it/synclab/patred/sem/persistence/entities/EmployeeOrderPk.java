package it.synclab.patred.sem.persistence.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class EmployeeOrderPk implements Serializable {
	
	private static final long serialVersionUID = 5167402927121497125L;
	
	private Employee employee;
	private Order order;
	
	public EmployeeOrderPk() {
	}
	
	@ManyToOne
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@ManyToOne
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
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
		EmployeeOrderPk other = (EmployeeOrderPk) obj;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "EmployeeOrderPk [Employee=" + employee + ", order=" + order + "]";
	}
	
}
