package it.synclab.patred.sem.webcontrollers.timesheet;

import it.synclab.patred.sem.Base;
import it.synclab.patred.sem.persistence.entities.Employee;
import it.synclab.patred.sem.persistence.entities.Roles;
import it.synclab.patred.sem.persistence.entities.Timesheet;
import it.synclab.patred.sem.persistence.entities.User;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TimesheetControllerTest extends Base {
	@Before
	public void init() {
		setCurrentTime(todate("16/06/2011"));
		cleanDatabase();
	}
	
	@Test
	public void createTimesheet() {
		
		Employee employee = createEmployee("employee");
		employee.setName("Nome");
		employee.setSurname("Cognome");
		
		Employee employee2 = createEmployee("employee2");
		employee.setName("Nome2");
		employee.setSurname("Cognome2");
		
		Timesheet timesheet1 = new Timesheet();
		timesheet1.setMonth(Calendar.MARCH);
		timesheet1.setYear(1998);
		timesheet1.setEmployee(employee);
		
		Timesheet timesheet2 = new Timesheet();
		timesheet2.setMonth(Calendar.JANUARY);
		timesheet2.setYear(2015);
		timesheet2.setEmployee(employee);
		
		Timesheet timesheet3 = new Timesheet();
		timesheet3.setMonth(Calendar.MARCH);
		timesheet3.setYear(2000);
		timesheet3.setEmployee(employee2);
		
		
		timesheetService.save(timesheet1);
		timesheetService.save(timesheet2);
		timesheetService.save(timesheet3);

		Assert.assertEquals(2, timesheetService.getByEmployee(employee).size());
		Assert.assertEquals(1, timesheetService.getByEmployee(employee2).size());

		Assert.assertEquals(2, employeeService.getAll().size());
		Assert.assertEquals(3, timesheetService.getAll().size());
		
		Assert.assertEquals(employeeService.getAll().get(0), timesheetService.getAll().get(0).getEmployee());
		Assert.assertEquals(employeeService.getAll().get(0), timesheetService.getAll().get(1).getEmployee());
	}
	
	private Employee createEmployee(String username) {
		User user = createUser(username, "password", Roles.Employee);
		return createEmployee(user);
	}
}
