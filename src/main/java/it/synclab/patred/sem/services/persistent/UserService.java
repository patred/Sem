package it.synclab.patred.sem.services.persistent;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Roles;
import it.synclab.patred.sem.persistence.entities.User;
import it.synclab.patred.sem.util.PasswordEncryption;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.inject.Singleton;

import org.hibernate.Query;

@Transactional
@Singleton
public class UserService extends BasePersistentService<User> {
	
	public UserService() {
	}
	
	public boolean authenticate(String username, String password) {
		
		boolean authenticate = false;
		User user = getByUsernameUser(username);
		if (user != null)
			try {
				authenticate = PasswordEncryption.authenticate(password, user.getPassword(), user.getSalt());
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				logger.warn("Password Encryption Failed: {}", e);
				return false;
			}
		
		return authenticate;
	}
	
	public User getByUsernameUser(String username) {
		
		Query query = session.getNamedQuery("getByUsernameUser");
		query.setParameter("username", username);
		return (User) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllByRoleUser(Roles role) {
		
		Query query = session.getNamedQuery("getAllByRoleUser");
		query.setParameter("role", role);
		
		return (List<User>) query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAvailableByRoleUser(Roles role, List<String> usernames) {
		
		Query query = session.getNamedQuery("getAvailableByRoleUser");
		query.setParameter("role", role);
		query.setParameterList("usernames", usernames);
		return (List<User>) query.list();
	}
}
