package it.synclab.patred.sem.services.persistent;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.TimesheetDetail;

import javax.inject.Singleton;

import org.hibernate.Query;

@Transactional
@Singleton
public class TimesheetDetailService extends BasePersistentService<TimesheetDetail> {
	
	public TimesheetDetailService() {
	}
	
	public TimesheetDetail get(Long id) {
		Query query = session.getNamedQuery("getTimesheetDetail");
		query.setParameter("id", id);
		return (TimesheetDetail) query.uniqueResult();
	}
}
