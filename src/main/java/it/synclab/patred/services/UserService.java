package it.synclab.patred.services;

import it.synclab.patred.persistence.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

@Singleton
public class UserService extends BaseService {
	private Map<String, User> userdb;
	
	public UserService() {
		userdb = new Hashtable<String, User>();
		for (int i = 0; i < 5; i++) {
			User user = new User();
			user.setName("name" + i);
			user.setSurname("surname" + i);
			user.setUsername("username" + i);
			user.setPassword("password" + i);
			userdb.put("user" + i, user);
		}
	}
	
	public List<User> getAll() {
		Collection<User> values = userdb.values();
		List<User> users = new ArrayList<User>();
		for (User user : values) {
			users.add(user);
		}
		
		return users;
	}
	
	public boolean authenticate(String user, String password) {
		if (userdb.containsKey(user)) {
			return userdb.get(user).getPassword().equals(password);
		}
		return false;
	}
	
	public User getUserFromUsername(String username) {
		return userdb.get(username);
	}
	
}
