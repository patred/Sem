package it.synclab.patred.sem.services.persistent;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Employee;
import it.synclab.patred.sem.persistence.entities.User;

import java.util.List;

import javax.inject.Singleton;

import org.hibernate.Query;

@Transactional
@Singleton
public class EmployeeService extends BasePersistentService<Employee> {
	
	public EmployeeService() {
	}
	
	public Employee get(Long id) {
		Query query = session.getNamedQuery("getEmployee");
		query.setParameter("id", id);
		return (Employee) query.uniqueResult();
	}
	
	public Employee getByUser(User user) {
		Query query = session.getNamedQuery("getByUserEmployee");
		query.setParameter("user", user);
		return (Employee) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getAllUsernames() {
		Query query = session.getNamedQuery("getAllUsernamesEmployee");
		return query.list();
	}
}
