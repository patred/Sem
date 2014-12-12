package it.synclab.patred.sem.services.persistent;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Manager;

import javax.inject.Singleton;

@Transactional
@Singleton
public class ManagerService extends BasePersistentService<Manager> {
	
	public ManagerService() {
	}
	
}