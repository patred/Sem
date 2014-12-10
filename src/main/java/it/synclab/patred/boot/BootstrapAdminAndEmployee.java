package it.synclab.patred.boot;

import it.synclab.patred.util.LogUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapAdminAndEmployee extends BaseBootstrap {
	
	private Logger logger = LoggerFactory.getLogger(LogUtils.formatClassName(this.getClass()));
	
	public void run() {
		
		String defaultAdmin = "Admin";
		String defaultEmployee = "Employee";
		String defaultPassword = "password";
		
		checkAndInsertManager(defaultAdmin, defaultPassword);
		checkAndInsertEmployee(defaultEmployee, defaultPassword);
		
		logger.info("End Insert/check Admin and first Employee.");
		
	}
	
}
