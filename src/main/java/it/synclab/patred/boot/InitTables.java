package it.synclab.patred.boot;

import it.synclab.patred.persistence.Manager;
import it.synclab.patred.persistence.Roles;
import it.synclab.patred.persistence.User;
import it.synclab.patred.persistence.services.ManagerService;
import it.synclab.patred.persistence.services.UserService;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitTables {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Inject
	private UserService userservice;
	
	@Inject
	private ManagerService managerservice;
	
	@Inject
	public InitTables() {
	}
	
	public void init() {
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
