package it.synclab.patred.sem.services.persistent;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Timesheet;
import it.synclab.patred.sem.persistence.entities.TimesheetDetail;

import java.util.List;

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
	
	@SuppressWarnings("unchecked")
	public List<TimesheetDetail> getTimesheetDetailByTimesheet(Timesheet timesheet) {
		Query query = session.getNamedQuery("getTimesheetDetailByTimesheet");
		query.setParameter("timesheet", timesheet);
		return (List<TimesheetDetail>) query.list();
	}
}
