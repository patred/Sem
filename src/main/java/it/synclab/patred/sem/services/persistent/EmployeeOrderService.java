package it.synclab.patred.sem.services.persistent;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.EmployeeOrder;

import javax.inject.Singleton;

@Transactional
@Singleton
public class EmployeeOrderService extends BasePersistentService<EmployeeOrder> {
	
	public EmployeeOrderService() {
	}
	
}
