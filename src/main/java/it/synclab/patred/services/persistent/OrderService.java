package it.synclab.patred.services.persistent;

import it.synclab.patred.annotations.Transactional;
import it.synclab.patred.persistence.entities.Employee;
import it.synclab.patred.persistence.entities.Order;

import javax.inject.Singleton;

@Transactional
@Singleton
public class OrderService extends BasePersistentService<Order> {
	
	public OrderService() {
	}
	
}
