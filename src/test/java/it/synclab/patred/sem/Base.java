package it.synclab.patred.sem;

import it.synclab.patred.sem.boot.Constants;
import it.synclab.patred.sem.fake.CalendarServiceMOCK;
import it.synclab.patred.sem.modules.SemModule;
import it.synclab.patred.sem.persistence.entities.Employee;
import it.synclab.patred.sem.persistence.entities.Manager;
import it.synclab.patred.sem.persistence.entities.Roles;
import it.synclab.patred.sem.persistence.entities.User;
import it.synclab.patred.sem.services.persistent.ClientService;
import it.synclab.patred.sem.services.persistent.EmployeeOrderService;
import it.synclab.patred.sem.services.persistent.EmployeeService;
import it.synclab.patred.sem.services.persistent.ManagerService;
import it.synclab.patred.sem.services.persistent.OrderService;
import it.synclab.patred.sem.services.persistent.TimesheetDetailService;
import it.synclab.patred.sem.services.persistent.TimesheetService;
import it.synclab.patred.sem.services.persistent.UserService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.easymock.EasyMock;
import org.junit.BeforeClass;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

public class Base {
	
	protected static Injector injector;
	protected static HttpServletRequest httpServletRequest;
	protected static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	/* Service */
	protected static UserService userService;
	protected static EmployeeService employeeService;
	protected static EmployeeOrderService employeeOrderService;
	protected static ManagerService managerService;
	protected static OrderService orderService;
	protected static ClientService clientService;
	protected static TimesheetService timesheetService;
	protected static TimesheetDetailService timesheetDetailService;
	protected static Constants constants;
	
	@BeforeClass
	public static void setup() {
		
		if (injector != null)
			return;
		
		System.out.println("Avvio ambiente di test!");
		
		// Uso i mock al posto delle classi che puntano ai sistemi esterni
		injector = Guice.createInjector(Modules.override(new SemModule()).with(new TModule()));
		
		userService = injector.getInstance(UserService.class);
		employeeService = injector.getInstance(EmployeeService.class);
		employeeOrderService = injector.getInstance(EmployeeOrderService.class);
		managerService = injector.getInstance(ManagerService.class);
		orderService = injector.getInstance(OrderService.class);
		clientService = injector.getInstance(ClientService.class);
		timesheetService = injector.getInstance(TimesheetService.class);
		timesheetDetailService = injector.getInstance(TimesheetDetailService.class);
		
		constants = injector.getInstance(Constants.class);
		
		httpServletRequest = EasyMock.createMock(HttpServletRequest.class);
		
		injector = injector.createChildInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(HttpServletRequest.class).toInstance(httpServletRequest);
			}
		});
		
	}
	
	protected Date todate(String string) {
		try {
			return sdf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setCurrentTime(Date todate) {
		CalendarServiceMOCK.setFixedCurrentDate(todate);
	}
	
	protected Employee createEmployee(User user) {
		Employee employee = employeeService.getByUser(user);
		if (employee == null) {
			employee = new Employee("Java Developer Expert");
			employee.setName("-");
			employee.setSurname("-");
			employee.setUser(user);
			employeeService.save(employee);
		}
		return employee;
	}
	
	protected Manager createManager(User user) {
		Manager manager = managerService.getByUser(user);
		if (manager == null) {
			manager = new Manager("Responsabile di Sistema");
			manager.setUser(user);
			managerService.save(manager);
		}
		return manager;
	}
	
	protected User createUser(String username, String password, Roles role) {
		
		User user = userService.getByUsernameUser(username);
		if (user == null) {
			try {
				user = new User(username, password, role);
				userService.save(user);
			} catch (NoSuchAlgorithmException e) {
				System.out.println("Password Encryption Failed: " + e);
			} catch (InvalidKeySpecException e) {
				System.out.println("Password Encryption Failed: " + e);
			}
		}
		return user;
	}
	
	protected void cleanDatabase() {
		clearClientTable();
		clearTimesheetDetailTable();
		clearTimesheetTable();
		clearEmployeeOrderTable();
		clearEmployeeTable();
		clearManagerTable();
		clearOrderTable();
		clearUserTable();
		
	}
	
	private void clearTimesheetDetailTable() {
		timesheetService.deleteAll();
	}
	
	private void clearUserTable() {
		userService.deleteAll();
	}
	
	private void clearEmployeeTable() {
		employeeService.deleteAll();
	}
	
	private void clearEmployeeOrderTable() {
		employeeOrderService.deleteAll();
	}
	
	private void clearManagerTable() {
		managerService.deleteAll();
	}
	
	private void clearOrderTable() {
		orderService.deleteAll();
	}
	
	private void clearClientTable() {
		clientService.deleteAll();
	}
	
	private void clearTimesheetTable() {
		timesheetService.deleteAll();
	}
	
}
