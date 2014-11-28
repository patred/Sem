package it.synclab.patred.persistence.services;

import it.synclab.patred.annotations.Transactional;
import it.synclab.patred.persistence.User;

import javax.inject.Singleton;

import org.hibernate.Query;

@Transactional
@Singleton
public class UserService extends BasePersistentService<User> {
	
	public UserService() {
	}
	
	public boolean authenticate(String username, String password) {
		User user = getUserFromUsername(username);
		if (user != null)
			return user.getPassword().equals(password);
		return false;
	}
	
	public User getUserFromUsername(String username) {
		
		Query query = session.getNamedQuery("getByUsernameUser");
		query.setParameter("username", username);
		return (User) query.uniqueResult();
	}
	
}
