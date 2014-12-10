package it.synclab.patred.services.persistent;

import it.synclab.patred.annotations.Transactional;
import it.synclab.patred.persistence.entities.Manager;

import javax.inject.Singleton;

@Transactional
@Singleton
public class ManagerService extends BasePersistentService<Manager> {
	
	public ManagerService() {
	}
	
}
