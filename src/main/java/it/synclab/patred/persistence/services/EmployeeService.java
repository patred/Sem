package it.synclab.patred.persistence.services;

import it.synclab.patred.annotations.Transactional;
import it.synclab.patred.persistence.Employee;

import javax.inject.Singleton;

@Transactional
@Singleton
public class EmployeeService extends BasePersistentService<Employee> {
	
	public EmployeeService() {
	}
	
}
