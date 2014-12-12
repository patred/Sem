package it.synclab.patred.webcontrollers;

import it.synclab.patred.util.LogUtils;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {
	protected Logger logger;
	protected Charset utf8Charser;
	
	public BaseController() {
		logger = LoggerFactory.getLogger(LogUtils.formatClassName(this.getClass()));
		utf8Charser = Charset.forName("UTF-8");
	}
}
