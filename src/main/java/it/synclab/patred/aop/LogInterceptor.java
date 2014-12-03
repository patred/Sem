package it.synclab.patred.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

public class LogInterceptor implements MethodInterceptor {
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		String classname = invocation.getMethod().getDeclaringClass().getSimpleName();
		String methodName = invocation.getMethod().getName();
		
		String args = Joiner.on(", ").useForNull("null").join(invocation.getArguments());
		
		logger.info("{}.{}({})", new Object[] { classname, methodName, args });
		
		Object output = invocation.proceed();
		
		logger.trace("Output: {}", output);
		
		return output;
	}
	
}