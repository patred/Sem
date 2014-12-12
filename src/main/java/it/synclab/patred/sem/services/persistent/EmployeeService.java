package it.synclab.patred.sem.services.persistent;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Employee;

import javax.inject.Singleton;

@Transactional
@Singleton
public class EmployeeService extends BasePersistentService<Employee> {
	
	public EmployeeService() {
	}
	
}
