package it.synclab.patred.services.persistent;

import it.synclab.patred.annotations.Transactional;
import it.synclab.patred.persistence.entities.Employee;

import javax.inject.Singleton;

@Transactional
@Singleton
public class EmployeeService extends BasePersistentService<Employee> {
	
	public EmployeeService() {
	}
	
}
