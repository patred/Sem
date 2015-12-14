package it.synclab.patred.sem.services;

import it.synclab.patred.sem.annotations.NoTransactional;
import it.synclab.patred.sem.util.LogUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class BaseService {
	
	protected Logger logger = LoggerFactory.getLogger(LogUtils.formatClassName(this.getClass()));
	
	@Inject
	protected CalendarService calendarService;
	
	@Override
	@NoTransactional
	protected void finalize() throws Throwable {
		super.finalize();
	}
}
