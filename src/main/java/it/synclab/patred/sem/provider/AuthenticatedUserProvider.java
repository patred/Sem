package it.synclab.patred.sem.provider;

import it.synclab.patred.sem.annotations.AuthenticatedUser;
import it.synclab.patred.sem.auth.LoginCookieManager;
import it.synclab.patred.sem.auth.UserEntry;
import it.synclab.patred.sem.persistence.entities.User;
import it.synclab.patred.sem.services.persistent.UserService;

import javax.ws.rs.ext.Provider;

import com.google.inject.Inject;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.api.core.HttpRequestContext;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.PerRequestTypeInjectableProvider;

@Provider
public class AuthenticatedUserProvider extends PerRequestTypeInjectableProvider<AuthenticatedUser, User> {
	
	@Inject
	private InjectableUser injectableUser;
	
	public AuthenticatedUserProvider() {
		super(User.class);
	}
	
	@Override
	public Injectable<User> getInjectable(ComponentContext ic, AuthenticatedUser a) {
		return injectableUser;
	}
	
}

class InjectableUser extends AbstractHttpContextInjectable<User> {
	
	@Inject
	private LoginCookieManager loginCookieManager;
	
	@Inject
	private UserService userService;
	
	@Override
	public User getValue(HttpContext context) {
		HttpRequestContext request = context.getRequest();
		UserEntry userEntry = loginCookieManager.getUserEntryFromCookies(request.getCookies());
		
		if(userEntry != null)
			return userService.getByUsernameUser(userEntry.getUserid());
		return null;
	}
	
}
