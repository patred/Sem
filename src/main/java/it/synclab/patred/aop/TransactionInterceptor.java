package it.synclab.patred.aop;

import it.synclab.patred.annotations.Transactional;
import it.synclab.patred.enumarations.TransactionType;
import it.synclab.patred.services.persistent.BasePersistentService;
import it.synclab.patred.services.persistent.HibernateSessionService;

import java.util.Stack;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

/**
 * Classe singleton che si occupa di gestire la sessione / transazione di
 * Hibernate
 * 
 * 
 */
public class TransactionInterceptor implements MethodInterceptor {
	
	private final ThreadLocal<Stack<Session>> sessionThreadLocal;
	
	@Inject
	private HibernateSessionService hibernateSessionService;
	
	public TransactionInterceptor() {
		
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		logger.info("Inizializzation of {}.", this.getClass().getCanonicalName());
		
		sessionThreadLocal = new ThreadLocal<Stack<Session>>() {
			@Override
			protected Stack<Session> initialValue() {
				return new Stack<Session>();
			}
		};
		
	}
	
	@SuppressWarnings("rawtypes")
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		Logger logger = LoggerFactory.getLogger(invocation.getMethod().getDeclaringClass());
		String methodName = invocation.getMethod().getName();
		TransactionType transType = TransactionType.Required;
		
		Transactional transactional = invocation.getMethod().getDeclaringClass().getAnnotation(Transactional.class);
		if (transactional != null)
			transType = transactional.value();
		
		Stack<Session> sessionStack = sessionThreadLocal.get();
		Transaction transaction = null;
		Session session = null;
		if (!sessionStack.isEmpty())
			session = sessionStack.peek();
		
		if (logger.isTraceEnabled()) {
			if (session == null) {
				logger.trace("Call transactional method <" + methodName + "> without session.");
			} else {
				logger.trace("Call transactional method <" + methodName + ">, session " + session.hashCode() + " and transaction " + session.getTransaction().hashCode() + ".");
			}
		}
		
		if (session == null) {
			
			boolean readonly = false;
			readonly = transType.equals(TransactionType.ReadOnly);
			
			session = hibernateSessionService.openSession(readonly);
			transaction = session.getTransaction();
			transaction.begin();
			sessionStack.push(session);
			
			if (logger.isTraceEnabled())
				logger.trace("Started new transaction with session " + session.hashCode() + " and transaction " + transaction.hashCode() + " Type: " + transType + ".");
		}
		
		Session oldSession = null;
		BasePersistentService service = null;
		if (invocation.getThis() instanceof BasePersistentService) {
			service = ((BasePersistentService) invocation.getThis());
			oldSession = service.getSession();
			service.setSession(session);
		}
		
		try {
			
			Object result = invocation.proceed();
			
			if (transaction != null) {
				if (logger.isTraceEnabled())
					logger.trace("Commit transaction, session " + session.hashCode() + " and transaction " + transaction.hashCode() + " Type " + transType + ".");
				transaction.commit();
			}
			
			return result;
			
		} catch (Exception e) {
			
			if (logger.isTraceEnabled())
				logger.trace("Rollback transaction, session " + session.hashCode() + " and transaction " + transaction.hashCode() + " Type " + transType + ".");
			if (transaction != null)
				transaction.rollback();
			throw e;
			
		} finally {
			
			logger.trace("Exit transactional method {}.", methodName);
			
			if (transaction != null) {
				hibernateSessionService.closeSession(session);
				sessionStack.pop();
				if (sessionStack.isEmpty())
					sessionThreadLocal.remove();
			}
			
			if (service != null)
				service.setSession(oldSession);
			
		}
		
	}
	
}