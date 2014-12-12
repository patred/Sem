package it.synclab.patred.services.persistent;

import it.synclab.patred.annotations.Transactional;
import it.synclab.patred.persistence.entities.EmployeeOrder;

import javax.inject.Singleton;

@Transactional
@Singleton
public class EmployeeOrderService extends BasePersistentService<EmployeeOrder> {
	
	public EmployeeOrderService() {
	}
	
}
