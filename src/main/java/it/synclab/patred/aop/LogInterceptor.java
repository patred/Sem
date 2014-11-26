package it.synclab.patred.aop;

import it.synclab.patred.util.LogUtil;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogInterceptor implements MethodInterceptor {
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		String classname = invocation.getMethod().getDeclaringClass().getSimpleName();
		String methodName = invocation.getMethod().getName();
		Object[] arguments = invocation.getArguments();
		String arg = LogUtil.getArguments(arguments);
		
		logger.info("{}.{}({})", new Object[] { classname, methodName, arg });
		
		try {
			return invocation.proceed();
			
		} finally {
		}
	}
	
}
