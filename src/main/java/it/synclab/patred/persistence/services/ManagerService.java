package it.synclab.patred.persistence.services;

import it.synclab.patred.annotations.Transactional;
import it.synclab.patred.persistence.Manager;

import javax.inject.Singleton;

@Transactional
@Singleton
public class ManagerService extends BasePersistentService<Manager> {
	
	public ManagerService() {
	}
	
}
