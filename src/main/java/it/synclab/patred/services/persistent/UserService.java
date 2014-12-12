package it.synclab.patred.services.persistent;

import it.synclab.patred.annotations.Transactional;
import it.synclab.patred.persistence.entities.Roles;
import it.synclab.patred.persistence.entities.User;

import javax.inject.Singleton;

import org.hibernate.Query;

@Transactional
@Singleton
public class UserService extends BasePersistentService<User> {
	
	public UserService() {
	}
	
	public boolean authenticate(String username, String password) {
		User user = getByUsernameUser(username);
		if (user != null)
			return user.getPassword().equals(password);
		return false;
	}
	
	public User getByUsernameUser(String username) {
		
		Query query = session.getNamedQuery("getByUsernameUser");
		query.setParameter("username", username);
		return (User) query.uniqueResult();
	}
	
	public User getAllByRoleUser(Roles role) {
		
		Query query = session.getNamedQuery("getAllByRoleUser");
		query.setParameter("role", role);
		return (User) query.uniqueResult();
	}
	
}
