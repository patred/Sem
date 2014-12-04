package it.synclab.patred.boot;

import it.synclab.patred.persistence.Manager;
import it.synclab.patred.persistence.Roles;
import it.synclab.patred.persistence.User;
import it.synclab.patred.persistence.services.EmployeeService;
import it.synclab.patred.persistence.services.ManagerService;
import it.synclab.patred.persistence.services.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;

public class Constants {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/* Services */
	private UserService userservice;
	private ManagerService managerservice;
	private EmployeeService employeeService;
	
	/* variables */
	private Properties prop;
	private String hostname;
	private String suffix;
	
	/* properties */
	private String urlDB;
	private String userDB;
	private String passwordDB;
	
	@Inject
	public Constants(Injector injector) {
		userservice = injector.getInstance(UserService.class);
		managerservice = injector.getInstance(ManagerService.class);
		employeeService = injector.getInstance(EmployeeService.class);
		
		initTables();
		setHostnameAndSuffix();
		logger.info("Suffix used: {}, Hostaname used: {}", this.suffix, this.hostname);
		init();
		
	}
	
	private String get(String key) {
		return this.prop.getProperty(this.suffix + key);
	}
	
	public String getUrlDB() {
		return urlDB;
	}
	
	public String getUserDB() {
		return userDB;
	}
	
	public String getPasswordDB() {
		return passwordDB;
	}
	
	private void setHostnameAndSuffix() {
		this.hostname = null;
		
		InetAddress inetAddr = null;
		try {
			inetAddr = InetAddress.getLocalHost();
			
			this.hostname = inetAddr.getHostName();
		} catch (UnknownHostException e) {
			logger.warn("Hostname not avaiable. Use local configuration", e);
		}
		
		/*
		 * TODO Esplicitare gli hostname
		 */
		if (this.hostname == null) {
			this.hostname = "localhost";
		}
		this.suffix = "local.";
		
		if ("PRODHOSTNAME".equals(this.hostname)) {
			this.suffix = "prod.";
			return;
		}
		if ("PREPRODHOSTNAME".equals(this.hostname)) {
			this.suffix = "preprod.";
			return;
		}
		if ("UATHOSTNAME".equals(this.hostname)) {
			this.suffix = "uat.";
			return;
		}
		
	}
	
	private void init() {
		
		prop = new Properties();
		InputStream input = null;
		
		try {
			
			String filename = "sem.properties";
			input = getClass().getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				logger.info("Sorry, unable to find {}", filename);
				return;
			}
			
			prop.load(input);
			
			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				logger.debug("Key : {}, Value : {}", key, value);
			}
			
			this.urlDB = get("url.db");
			this.passwordDB = get("username.db");
			this.userDB = get("password.db");
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private void initTables() {
		String defaultAdmin = "Admin";
		String defaultPassword = "password";
		
		User user = userservice.getUserFromUsername(defaultAdmin);
		
		if (user == null) {
			user = new User(defaultAdmin, defaultPassword);
			user.setName("-");
			user.setSurname("-");
			user.setRole(Roles.Manager);
			Manager manager = new Manager("Responsabile di Sistema");
			managerservice.save(manager);
			user.setManager(manager);
			userservice.save(user);
		}
		
	}
	
}