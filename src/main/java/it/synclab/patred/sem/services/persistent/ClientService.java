package it.synclab.patred.sem.services.persistent;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Client;

import javax.inject.Singleton;

import org.hibernate.Query;

@Transactional
@Singleton
public class ClientService extends BasePersistentService<Client> {
	
	public ClientService() {
	}

	public Client get(Long id) {
		Query query = session.getNamedQuery("getClient");
		query.setParameter("id", id);
		return (Client) query.uniqueResult();
	}
	
}

