package it.synclab.patred.sem.services.persistent;

import it.synclab.patred.sem.util.LogUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

@Singleton
public class HibernateSessionService {
	
	private Logger logger = LoggerFactory.getLogger(LogUtils.formatClassName(this.getClass()));
	
	private SessionFactory sessionFactory;
	
	public synchronized SessionFactory getSessionFactory() {
		
		if (sessionFactory != null)
			return sessionFactory;
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		logger.info("Hibernate Configuration loaded successfully.");
		
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		logger.info("Hibernate serviceRegistry created successfully.");
		
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		logger.info("Hibernate SessionFactory build successfully.");
		
		return sessionFactory;
		
	}
	
	public Session openSession(boolean readonly) {
		Session session = getSessionFactory().openSession();
		session.setDefaultReadOnly(readonly);
		logger.trace("Open session " + session.hashCode() + " defReadOnly: " + readonly + ".");
		return session;
	}
	
	public void closeSession(Session session) {
		
		if (session != null) {
			
			logger.trace("Close session {}.", session.hashCode());
			
			if (session.isConnected()) {
				
				session.clear();
				Transaction t = session.getTransaction();
				
				if (t.isActive())
					t.rollback();
				session.flush();
			}
			
			if (session.isOpen())
				session.close();
		}
	}
	
	public synchronized void shutdown() {
		
		try {
			
			if (sessionFactory != null)
				sessionFactory.close();
			
			logger.info("Hibernate SessionFactory closed successfully.");
			
		} catch (Exception e) {
			logger.warn("Error shutting down hibernate session factory.", e);
		}
		
	}
	
}
