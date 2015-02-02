package it.synclab.patred.sem.aop;

import it.synclab.patred.sem.annotations.AuthenticatedUser;
import it.synclab.patred.sem.exception.NotAuthorizedException;

import java.lang.reflect.Field;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MustAuthenticateInterceptor implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {

		Field[] fields = invocation.getThis().getClass().getSuperclass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(AuthenticatedUser.class)) {
				field.setAccessible(true);
				if (field.get(invocation.getThis()) == null)
					throw new NotAuthorizedException();
				else
					return invocation.proceed();
			}
		}

		throw new RuntimeException("There isn't annotation to inject AutenticatedUser!");

	}
}
