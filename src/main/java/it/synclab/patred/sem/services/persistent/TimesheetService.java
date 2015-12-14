package it.synclab.patred.sem.services.persistent;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Employee;
import it.synclab.patred.sem.persistence.entities.Timesheet;

import java.util.List;

import javax.inject.Singleton;

import org.hibernate.Query;

@Transactional
@Singleton
public class TimesheetService extends BasePersistentService<Timesheet> {
	
	public TimesheetService() {
	}
	
	public Timesheet get(Long id) {
		Query query = session.getNamedQuery("getTimesheet");
		query.setParameter("id", id);
		return (Timesheet) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Timesheet> getByEmployee(Employee employee) {
		Query query = session.getNamedQuery("getByEmployeeTimesheet");
		query.setParameter("employee", employee);
		return query.list();
	}
	
}
