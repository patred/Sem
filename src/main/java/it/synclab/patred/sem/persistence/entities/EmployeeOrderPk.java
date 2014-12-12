package it.synclab.patred.sem.persistence.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class EmployeeOrderPk implements Serializable {
	
	private static final long serialVersionUID = 5167402927121497125L;
	
	private Employee Employee;
	private Order order;
	
	public EmployeeOrderPk() {
	}
	
	@ManyToOne
	public Employee getEmployee() {
		return Employee;
	}
	
	public void setEmployee(Employee employee) {
		Employee = employee;
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
		result = prime * result + ((Employee == null) ? 0 : Employee.hashCode());
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
		if (Employee == null) {
			if (other.Employee != null)
				return false;
		} else if (!Employee.equals(other.Employee))
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
		return "EmployeeOrderPk [Employee=" + Employee + ", order=" + order + "]";
	}
	
}
