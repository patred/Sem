package it.synclab.patred.sem;

import it.synclab.patred.sem.boot.Constants;
import it.synclab.patred.sem.fake.CalendarServiceMOCK;
import it.synclab.patred.sem.modules.SemModule;
import it.synclab.patred.sem.services.persistent.EmployeeOrderService;
import it.synclab.patred.sem.services.persistent.EmployeeService;
import it.synclab.patred.sem.services.persistent.ManagerService;
import it.synclab.patred.sem.services.persistent.OrderService;
import it.synclab.patred.sem.services.persistent.UserService;

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
		
		constants = injector.getInstance(Constants.class);
		
		httpServletRequest = EasyMock.createMock(HttpServletRequest.class);
		
		injector = injector.createChildInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(HttpServletRequest.class).toInstance(httpServletRequest);
			}
		});
		
		checkInitialData();
	}
	
	private static void checkInitialData() {
		
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
	
	protected void cleanDatabase() {
		clearOrderService();
		clearManagerService();
		clearEmployeeOrderService();
		clearEmployeeService();
		clearUserService();
		checkInitialData();
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
	
}