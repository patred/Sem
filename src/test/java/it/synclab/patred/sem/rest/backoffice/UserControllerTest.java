package it.synclab.patred.sem.rest.backoffice;

import it.synclab.patred.sem.Base;
import it.synclab.patred.sem.persistence.entities.Roles;
import it.synclab.patred.sem.persistence.entities.User;

import java.util.List;

import javax.ws.rs.core.GenericEntity;
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
		UserController controller = injector.getInstance(UserController.class);
		User createEmployee = createEmployee("employee");
		createEmployee("employee2");
		User createManager = createManager("Manager");
		
		List<User> managers = ((GenericEntity<List<User>>) controller.getAllByRoleUser("mAnAgEr").getEntity()).getEntity();
		List<User> employees = ((GenericEntity<List<User>>) controller.getAllByRoleUser("EmPlOyEe").getEntity()).getEntity();
		
		int status = ((Response) controller.getAllByRoleUser("dehudh")).getStatus();
		
		Assert.assertEquals(1, managers.size());
		Assert.assertEquals(2, employees.size());
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), status);
		Assert.assertEquals(3, controller.getAll().size());
		Assert.assertEquals(createManager, managers.get(0));
		Assert.assertEquals(createEmployee, employees.get(0));
		
	}
	
	private User createEmployee(String username) {
		
		return createUser(username, "password", Roles.Employee);
	}
	
	private User createManager(String username) {
		return createUser(username, "password", Roles.Manager);
		
	}
	
}
