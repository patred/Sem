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
	
	protected User createUser(String username, String password, Roles role) {
		
		User user = userService.getByUsernameUser(username);
		if (user == null) {
			try {
				user = new User(username, password, role);
			} catch (NoSuchAlgorithmException e) {
				System.out.println("Password Encryption Failed: " + e);
			} catch (InvalidKeySpecException e) {
				System.out.println("Password Encryption Failed: " + e);
			}
			user.setName("-");
			user.setSurname("-");
			
			switch (role) {
				case Employee:
					Employee employee = new Employee("Java Developer Expert");
					employeeService.save(employee);
					user.setEmployee(employee);
					
					break;
				case Manager:
					Manager manager = new Manager("Responsabile di Sistema");
					managerService.save(manager);
					user.setManager(manager);
					break;
				default:
					break;
			}
			
			userService.save(user);
		}
		return user;
	}
	
	protected void cleanDatabase() {
		clearEmployeeOrderService();
		clearEmployeeService();
		clearManagerService();
		clearOrderService();
		clearUserService();
		clearClientService();
		
	}
	
	private void clearUserService() {
		userService.deleteAll();
	}
	
	private void clearEmployeeService() {
		employeeService.deleteAll();
	}
	
	private void clearEmployeeOrderService() {
		employeeOrderService.deleteAll();
	}
	
	private void clearManagerService() {
		managerService.deleteAll();
	}
	
	private void clearOrderService() {
		orderService.deleteAll();
	}
	
	private void clearClientService() {
		clientService.deleteAll();
	}
	
}
