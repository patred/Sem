package it.synclab.patred.services;

import it.synclab.patred.annotations.NoTransactional;
import it.synclab.patred.util.LogUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseService {
	protected Logger logger = LoggerFactory.getLogger(LogUtils.formatClassName(this.getClass()));
	
	@Override
	@NoTransactional
	protected void finalize() throws Throwable {
		super.finalize();
	}
}
