package it.synclab.patred.sem.boot;

import it.synclab.patred.sem.util.LogUtils;

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
	/* roles */
	public static final String ROLE_DEV = "devsem";
	public static final String ROLE_MANAGER = "semmanager";
	public static final String ROLE_OPERATOR = "semoperator";
	
	/* suffix */
	public static final String SUFFIX_LOACL = "local.";
	public static final String SUFFIX_UAT = "uat.";
	public static final String SUFFIX_PREPROD = "preprod.";
	public static final String SUFFIX_PROD = "prod.";

	
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
		
		suffix = SUFFIX_LOACL;
		
		hostname = System.getenv("HOSTNAME");
		if (hostname != null) {
			if (hostname.toLowerCase(Locale.ITALIAN).startsWith("preprod")) {
				suffix = SUFFIX_PREPROD;
			} else if (hostname.toLowerCase(Locale.ITALIAN).startsWith("prod")) {
				suffix = SUFFIX_PROD;
			} else if (hostname.toLowerCase(Locale.ITALIAN).startsWith("uat")) {
				suffix = SUFFIX_UAT;
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
	
	public boolean isLocal() {
		return SUFFIX_LOACL.equals(this.suffix);
	}
	
	public boolean isUat() {
		return SUFFIX_UAT.equals(this.suffix);
	}
	
	public boolean isPreprod() {
		return SUFFIX_PREPROD.equals(this.suffix);
	}
	
	public boolean isProd() {
		return SUFFIX_PROD.equals(this.suffix);
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