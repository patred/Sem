package it.synclab.patred.sem.aop;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.synclab.patred.sem.exception.SemRestException;
import it.synclab.patred.sem.util.LogUtils;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManageOutputExceptionInterceptor implements MethodInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(LogUtils.formatClassName(this.getClass()));
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		try {
			return invocation.proceed();
		} catch (SemRestException e) {
			switch (e.getLogLevel()) {
				case trace:
					logger.trace(e.getMessage());
					break;
				case debug:
					logger.debug(e.getMessage());
					break;
				case info:
					logger.info(e.getMessage());
					break;
				case warn:
					logger.warn(e.getMessage(), e);
					break;
				
				default:
					logger.error(e.getMessage(), e);
					break;
			}
			return Response.status(e.getStatus()).entity(e.getMessage()).build();
		} catch (Exception e) {
			logger.error("Errore intercettato da ManageOutputExceptionInterceptor.", e);
			throw new SemRestException(e.getMessage(), e.getCause());
		}
	}
	
}
