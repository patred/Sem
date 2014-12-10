package it.synclab.patred.boot;

import it.synclab.patred.util.LogUtils;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

@Singleton
public class Constants {
	
	private Logger logger = LoggerFactory.getLogger(LogUtils.formatClassName(this.getClass()));
	
	/* variables */
	private Properties props;
	private String hostname;
	private String suffix;
	
	/* properties */
	private String urlDB;
	private String userDB;
	private String passwordDB;
	
	@Inject
	public Constants() {
		
		suffix = "local.";
		
		hostname = System.getenv("HOSTNAME");
		if (hostname != null) {
			if (hostname.toLowerCase(Locale.ITALIAN).startsWith("preprod")) {
				suffix = "preprod.";
			} else if (hostname.toLowerCase(Locale.ITALIAN).startsWith("prod")) {
				suffix = "prod.";
			} else if (hostname.toLowerCase(Locale.ITALIAN).startsWith("uat")) {
				suffix = "uat.";
			}
		} else {
			hostname = "HOSTNAME";
			logger.error("Variable Environment 'HOSTNAME' not defined, used 'local.' as defaul value to 'suffix' variable.");
		}
		
		logger.info("Suffix used: {}, Hostaname used: {}", new Object[] { this.suffix, this.hostname });
		props = new Properties();
		
		try {
			
			props.load(Constants.class.getClassLoader().getResourceAsStream("sem.properties"));
			urlDB = get("url.db");
			userDB = get("username.db");
			passwordDB = get("password.db");
			
		} catch (IOException ioe) {
			logger.error("Properties file not loaded!", ioe);
		}
		
	}
	
	public String get(String key) {
		return (String) props.get(suffix + key);
	}
	
	public String getUrlDB() {
		return urlDB;
	}
	
	public String getUserDB() {
		return userDB;
	}
	
	public String getPasswordDB() {
		return passwordDB;
	}
	
}