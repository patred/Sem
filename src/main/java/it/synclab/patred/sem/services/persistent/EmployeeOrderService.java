package it.synclab.patred.sem.services.persistent;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Employee;
import it.synclab.patred.sem.persistence.entities.EmployeeOrder;
import it.synclab.patred.sem.persistence.entities.Order;

import java.util.List;

import javax.inject.Singleton;

import org.hibernate.Query;

@Transactional
@Singleton
public class EmployeeOrderService extends BasePersistentService<EmployeeOrder> {
	
	public EmployeeOrderService() {
	}
	
	@SuppressWarnings("unchecked")
	public List<EmployeeOrder> getByEmployee(Employee employee) {
		Query query = session.getNamedQuery("getByEmployeeClient");
		query.setParameter("employee", employee);
		return (List<EmployeeOrder>) query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<EmployeeOrder> getByOrder(Order order) {
		Query query = session.getNamedQuery("getByOrderClient");
		query.setParameter("order", order);
		return (List<EmployeeOrder>) query.list();
	}
	
}
