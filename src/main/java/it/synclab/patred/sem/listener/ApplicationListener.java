package it.synclab.patred.sem.listener;

import it.synclab.patred.sem.boot.BootstrapAdminAndEmployee;
import it.synclab.patred.sem.boot.Constants;
import it.synclab.patred.sem.modules.SemWebModule;
import it.synclab.patred.sem.services.persistent.HibernateSessionService;
import it.synclab.patred.sem.util.LogUtils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

import oracle.ucp.UniversalConnectionPoolException;
import oracle.ucp.admin.UniversalConnectionPoolManagerImpl;
import oracle.ucp.jdbc.PoolDataSourceImpl;

import org.hibernate.jmx.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import ch.qos.logback.classic.LoggerContext;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class ApplicationListener extends GuiceServletContextListener {
	private static Logger logger = LoggerFactory.getLogger(LogUtils.formatClassName(ApplicationListener.class));
	
	private final static String HIBERSTATS_HIEARCHY_DEFAULT = "sem.hibernate:type=statistics";
	
	private Injector injector;
	
	private PoolDataSourceImpl semDbPoolDataSourceImpl;
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		
		logger.info("Start Application - contextInitialized");
		
	}
	
	@Override
	protected Injector getInjector() {
		logger.info("create INJECTOR...");
		
		injector = Guice.createInjector(new SemWebModule());
		
		SLF4JBridgeHandler.install();
		
		changeContextParameter();
		/**
		 * TODO deprecated version 4.x of hibernate check alternative
		 * registerHibernateStatistics();
		 */
		
		checkAllDefaultDataOnBD();
		return injector;
		
	}
	
	private void changeContextParameter() {
		
		Context initContext = null;
		try {
			
			Constants constants = injector.getInstance(Constants.class);
			
			initContext = new InitialContext();
			
			DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/semDbConnPool");
			
			semDbPoolDataSourceImpl = (PoolDataSourceImpl) ds;
			semDbPoolDataSourceImpl.setURL(constants.getUrlDB());
			semDbPoolDataSourceImpl.setUser(constants.getUserDB());
			semDbPoolDataSourceImpl.setPassword(constants.getPasswordDB());
			
			logger.info("context.xml changed.");
			
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			if (initContext != null) {
				try {
					initContext.close();
				} catch (NamingException e) {
					logger.warn("Can't close Initial Context", e);
				}
			}
		}
	}
	
	/**
	 * TODO
	 */
	private void registerHibernateStatistics() {
		
		HibernateSessionService hibernateSessionService = injector.getInstance(HibernateSessionService.class);
		
		MBeanServer mbeanServer = findMBeanServer();
		
		try {
			ObjectName objectName = new ObjectName(HIBERSTATS_HIEARCHY_DEFAULT);
			
			StatisticsService mBean = new StatisticsService();
			mBean.setStatisticsEnabled(true);
			mBean.setSessionFactory(hibernateSessionService.getSessionFactory());
			mbeanServer.registerMBean(mBean, objectName);
			logger.info("Register hibernate statistics mbean successfull");
			
		} catch (Exception e) {
			logger.error("Cannot register mbean hibernate statistics", e);
		}
	}
	
	private MBeanServer findMBeanServer() {
		List<MBeanServer> serverList = MBeanServerFactory.findMBeanServer(null);
		if (serverList == null || serverList.size() == 0)
			return null;
		return serverList.get(0);
	}
	
	private void checkAllDefaultDataOnBD() {
		
		BootstrapAdminAndEmployee boot = injector.getInstance(BootstrapAdminAndEmployee.class);
		try {
			boot.run();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			logger.warn("Password Encryption Failed: {}", e);
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
		logger.info("Destroy application...");
		
		try {
			MBeanServer server = findMBeanServer();
			ObjectName qmbo = new ObjectName("sem.hibernate:*");
			Set<ObjectName> names = server.queryNames(qmbo, null);
			for (ObjectName name : names)
				server.unregisterMBean(name);
			logger.info("Unregister hibernate statistics mbean successfull");
		} catch (Exception e) {
			logger.error("[contextDestroyed]: Exception catched: ", e);
		}
		
		HibernateSessionService hibernateSessionService = injector.getInstance(HibernateSessionService.class);
		hibernateSessionService.shutdown();
		try {
			LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
			if (lc.isStarted())
				lc.stop();
			else
				lc = null;
			logger.info("Stopping LoggerContext successfull");
		} catch (Exception e) {
			logger.error("Error destroing LoggerContext.", e);
		}
		String poolName = semDbPoolDataSourceImpl.getConnectionPoolName();
		try {
			
			UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager().destroyConnectionPool(poolName);
			logger.info("Destroy Universal Connection Pool successfull");
			
		} catch (UniversalConnectionPoolException e) {
			logger.error("Error destroing UniversalConnectionPool {}.", poolName, e);
		}
		
		super.contextDestroyed(servletContextEvent);
		
	}
}
