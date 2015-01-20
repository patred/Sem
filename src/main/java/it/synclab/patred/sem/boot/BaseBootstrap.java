package it.synclab.patred.sem.boot;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import it.synclab.patred.sem.persistence.entities.Employee;
import it.synclab.patred.sem.persistence.entities.Manager;
import it.synclab.patred.sem.persistence.entities.Roles;
import it.synclab.patred.sem.persistence.entities.User;
import it.synclab.patred.sem.services.persistent.EmployeeService;
import it.synclab.patred.sem.services.persistent.ManagerService;
import it.synclab.patred.sem.services.persistent.UserService;

import com.google.inject.Inject;

public class BaseBootstrap {
	
	@Inject
	protected EmployeeService employeeService;
	
	@Inject
	protected ManagerService managerService;
	
	@Inject
	protected UserService userService;
	
	protected User checkAndInsertManager(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		User user = userService.getByUsernameUser(username);
		if (user == null) {
			
			user = new User(username, password);
			user.setRole(Roles.Manager);
			userService.save(user);
			
			Manager manager = new Manager("Responsabile di Sistema");
			manager.setUser(user);
			managerService.save(manager);
		}
		return user;
	}
	
	protected User checkAndInsertEmployee(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		User user = userService.getByUsernameUser(username);
		if (user == null) {
			
			user = new User(username, password);
			user.setRole(Roles.Employee);
			userService.save(user);
			
			Employee employee = new Employee("Java Developer Expert");
			employee.setName("-");
			employee.setSurname("-");
			employee.setUser(user);
			employeeService.save(employee);
		}
		return user;
	}
	
}
