package it.synclab.patred.sem.modules;

import it.synclab.patred.sem.annotations.MustAuthenticate;
import it.synclab.patred.sem.annotations.NoTransactional;
import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.aop.LogInterceptor;
import it.synclab.patred.sem.aop.MustAuthenticateInterceptor;
import it.synclab.patred.sem.aop.TransactionInterceptor;
import it.synclab.patred.sem.aop.TrimAndNullInterceptor;
import it.synclab.patred.sem.util.EmptyUriInfo;

import java.lang.reflect.Method;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.UriInfo;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matchers;

public class SemModule extends AbstractModule {
	
	private final class IsNotSyntheticMatcher extends AbstractMatcher<Method> {
		public boolean matches(Method t) {
			return !t.isSynthetic();
		}
	}
	
	@Override
	protected void configure() {
		
		bind(UriInfo.class).to(EmptyUriInfo.class);
		
		// AOP per transazioni
		TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
		requestInjection(transactionInterceptor);
		
		bindInterceptor(Matchers.annotatedWith(Transactional.class), new IsNotSyntheticMatcher().and(Matchers.not(Matchers.annotatedWith(NoTransactional.class))),
				transactionInterceptor);
		
		// AOP per log di chiamate, TrimAndNullable
		LogInterceptor logInterceptor = new LogInterceptor();
		TrimAndNullInterceptor trimAndNullInterceptor = new TrimAndNullInterceptor();
		bindInterceptor(Matchers.annotatedWith(Path.class),
				Matchers.annotatedWith(GET.class).or(Matchers.annotatedWith(POST.class)).or(Matchers.annotatedWith(PUT.class)).or(Matchers.annotatedWith(DELETE.class)),
				trimAndNullInterceptor, logInterceptor);
		
		// AOP per l'autenticazione
		MustAuthenticateInterceptor mustAuth = new MustAuthenticateInterceptor();
		requestInjection(mustAuth);

		bindInterceptor(Matchers.any(), Matchers.annotatedWith(MustAuthenticate.class), mustAuth);
		bindInterceptor(Matchers.annotatedWith(MustAuthenticate.class), Matchers.any(), mustAuth);
		
	}
}