package it.synclab.patred.sem.services.persistent;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Order;

import javax.inject.Singleton;

@Transactional
@Singleton
public class OrderService extends BasePersistentService<Order> {
	
	public OrderService() {
	}
	
}
