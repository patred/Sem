package it.synclab.patred.sem.rest;

import it.synclab.patred.sem.boot.Constants;
import it.synclab.patred.sem.services.CalendarService;
import it.synclab.patred.sem.services.cluster.UserTokenService;
import it.synclab.patred.sem.util.LogUtils;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class BaseController {
	protected Logger logger;
	protected Charset utf8Charser;
	
	@Inject
	protected CalendarService calendarService;
	
	@Inject
	protected Constants constants;
	
	@Inject
	protected UserTokenService userTokenService;
	
	public BaseController() {
		logger = LoggerFactory.getLogger(LogUtils.formatClassName(this.getClass()));
		utf8Charser = Charset.forName("UTF-8");
	}
}
