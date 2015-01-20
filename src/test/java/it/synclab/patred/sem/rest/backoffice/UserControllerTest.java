package it.synclab.patred.sem.rest.backoffice;

import it.synclab.patred.sem.Base;
import it.synclab.patred.sem.persistence.entities.Employee;
import it.synclab.patred.sem.persistence.entities.Manager;
import it.synclab.patred.sem.persistence.entities.Roles;
import it.synclab.patred.sem.persistence.entities.User;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserControllerTest extends Base {
	
	@Before
	public void init() {
		setCurrentTime(todate("16/06/2011"));
		cleanDatabase();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void createUser() {
		EmployeeController eController = injector.getInstance(EmployeeController.class);
		ManagerController mController = injector.getInstance(ManagerController.class);
		UserController uController = injector.getInstance(UserController.class);
		
		Employee createEmployee = createEmployee("employee");
		createEmployee = createEmployee("employee2");
		Manager createManager = createManager("Manager");
		
		List<Manager> managers = mController.getAll();
		List<Employee> employees = eController.getAll();
		List<User> users = uController.getAll();
		
		Assert.assertEquals(1, managers.size());
		Assert.assertEquals(2, employees.size());
		Assert.assertEquals(3, users.size());
		Assert.assertEquals(createManager, managers.get(0));
		Assert.assertEquals(createEmployee, employees.get(1));
		
	}
	
	private Employee createEmployee(String username) {
		User user = createUser(username, "password", Roles.Employee);
		return createEmployee(user);
	}
	
	private Manager createManager(String username) {
		User user = createUser(username, "password", Roles.Manager);
		return createManager(user);
	}
	
}
