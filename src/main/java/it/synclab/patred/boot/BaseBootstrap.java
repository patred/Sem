package it.synclab.patred.boot;

import it.synclab.patred.persistence.entities.Employee;
import it.synclab.patred.persistence.entities.Manager;
import it.synclab.patred.persistence.entities.Roles;
import it.synclab.patred.persistence.entities.User;
import it.synclab.patred.services.persistent.EmployeeService;
import it.synclab.patred.services.persistent.ManagerService;
import it.synclab.patred.services.persistent.UserService;

import com.google.inject.Inject;

public class BaseBootstrap {
	
	@Inject
	protected EmployeeService employeeService;
	
	@Inject
	protected ManagerService managerService;
	
	@Inject
	protected UserService userService;
	
	protected User checkAndInsertManager(String username, String password) {
		
		User user = userService.getUserFromUsername(username);
		if (user == null) {
			
			user = new User(username, password);
			user.setName("-");
			user.setSurname("-");
			user.setRole(Roles.Manager);
			Manager manager = new Manager("Responsabile di Sistema");
			managerService.save(manager);
			user.setManager(manager);
			userService.save(user);
		}
		return user;
	}
	
	protected User checkAndInsertEmployee(String username, String password) {
		
		User user = userService.getUserFromUsername(username);
		if (user == null) {
			
			user = new User(username, password);
			user.setName("-");
			user.setSurname("-");
			user.setRole(Roles.Employee);
			Employee employee = new Employee("Java Developer Expert");
			employeeService.save(employee);
			user.setEmployee(employee);
			userService.save(user);
		}
		return user;
	}
	
}