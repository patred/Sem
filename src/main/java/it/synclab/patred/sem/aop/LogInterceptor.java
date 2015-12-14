package it.synclab.patred.sem.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

public class LogInterceptor implements MethodInterceptor {
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		Class<?> declaringClass = invocation.getMethod().getDeclaringClass();
		Logger logger = LoggerFactory.getLogger(declaringClass);
		
		String methodName = invocation.getMethod().getName();
		
		String args = Joiner.on(", ").useForNull("null").join(invocation.getArguments());
		
		logger.info("{}({})", new Object[] { methodName, args });
		
		Object output = invocation.proceed();
		
		logger.trace("Output: {}", output);
		
		return output;
	}
	
}