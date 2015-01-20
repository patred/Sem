package it.synclab.patred.sem.services.persistent;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Manager;
import it.synclab.patred.sem.persistence.entities.User;

import java.util.List;

import javax.inject.Singleton;

import org.hibernate.Query;

@Transactional
@Singleton
public class ManagerService extends BasePersistentService<Manager> {
	
	public ManagerService() {
	}
	
	public Manager get(Long id) {
		Query query = session.getNamedQuery("getManager");
		query.setParameter("id", id);
		return (Manager) query.uniqueResult();
	}
	
	public Manager getByUser(User user) {
		Query query = session.getNamedQuery("getByUserManager");
		query.setParameter("user", user);
		return (Manager) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getAllUsernames() {
		Query query = session.getNamedQuery("getAllUsernamesManager");
		return query.list();
	}
}