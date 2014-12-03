package it.synclab.patred.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TrimAndNullInterceptor implements MethodInterceptor {
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		for (int i = 0; i < invocation.getArguments().length; i++) {
			if (invocation.getArguments()[i] != null && invocation.getArguments()[i] instanceof String) {
				String trim = ((String) invocation.getArguments()[i]).trim();
				if (trim.isEmpty()) {
					invocation.getArguments()[i] = null;
				} else {
					invocation.getArguments()[i] = trim;
				}
				
			}
			
		}
		return invocation.proceed();
	}
	
}
