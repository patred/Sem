package it.synclab.patred.sem.rest;

import it.synclab.patred.sem.annotations.AuthenticatedUser;
import it.synclab.patred.sem.annotations.MustAuthenticate;
import it.synclab.patred.sem.auth.LoginCookieManager;
import it.synclab.patred.sem.persistence.entities.User;
import it.synclab.patred.sem.services.cluster.UserTokenService;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.sun.jersey.spi.resource.PerRequest;

@PerRequest
@Path("services/logout")
public class LogoutController extends BaseController {
	
	@AuthenticatedUser
	private User user;
	
	@Inject
	private UserTokenService userTokenService;
	
	@Inject
	private LoginCookieManager loginCookieManager;
	
	@Inject
	public LogoutController() {
	}
	
	@GET
	@MustAuthenticate
	@Produces("text/html; charset=utf-8")
	public Response logout(@QueryParam("from") String from) throws URISyntaxException {
		String seeOther = from == null ? "/" : from;
		if("backoffice".equals(from))
			seeOther = "/" + from;
		if (user != null) {
			userTokenService.remove(user.getUsername());
			ResponseBuilder page = Response.seeOther(new URI(seeOther));
			page = loginCookieManager.logout(page);
			return page.build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
}
