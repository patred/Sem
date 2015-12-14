package it.synclab.patred.sem.aop;

import it.synclab.patred.sem.annotations.AuthenticatedUser;
import it.synclab.patred.sem.exception.SemRestException;
import it.synclab.patred.sem.util.LogLevel;

import java.lang.reflect.Field;

import javax.ws.rs.core.Response.Status;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MustAuthenticateInterceptor implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {

		Field[] fields = invocation.getThis().getClass().getSuperclass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(AuthenticatedUser.class)) {
				field.setAccessible(true);
				if (field.get(invocation.getThis()) == null)
					throw new SemRestException(LogLevel.info, Status.UNAUTHORIZED, Status.UNAUTHORIZED.getStatusCode());
				else
					return invocation.proceed();
			}
		}

		throw new SemRestException("There isn't annotation to inject AutenticatedUser!");

	}
}
