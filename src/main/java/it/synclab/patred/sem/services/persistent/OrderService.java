package it.synclab.patred.sem.services.persistent;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Order;

import javax.inject.Singleton;

import org.hibernate.Query;

@Transactional
@Singleton
public class OrderService extends BasePersistentService<Order> {
	
	public OrderService() {
	}

	public Order get(Long id) {
		Query query = session.getNamedQuery("getOrder");
		query.setParameter("id", id);
		return (Order) query.uniqueResult();
	}
	
}

