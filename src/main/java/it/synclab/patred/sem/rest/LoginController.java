package it.synclab.patred.sem.rest;

import it.synclab.patred.sem.annotations.AuthenticatedUser;
import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.auth.LoginCookieManager;
import it.synclab.patred.sem.auth.UserEntry;
import it.synclab.patred.sem.persistence.entities.User;
import it.synclab.patred.sem.services.cluster.UserTokenService;
import it.synclab.patred.sem.services.persistent.UserService;

import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.sun.jersey.spi.resource.PerRequest;

@Transactional
@PerRequest
@Path("services/login")
public class LoginController extends BaseController {
	
	@AuthenticatedUser
	private User user;
	
	@Inject
	private UserService userservice;
	
	@Inject
	private UserTokenService userTokenService;
	
	@Inject
	private LoginCookieManager loginCookieManager;
	
	@Inject
	public LoginController() {
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response isLogged(){
		if (user != null) {
			logger.info(user.getUsername() + " loggato!");
			return Response.noContent().build();
		}
		
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {
		
		if (user != null) {
			logger.info("loggato!");
			return Response.noContent().build();
		}
		
		if (userservice.authenticate(username, password)) {
			UserEntry userEntry = userTokenService.put(username);
			Date expirationDate = userTokenService.getExpirationDate(username);
			return loginCookieManager.login(Response.noContent(), username, userEntry.getToken(), expirationDate).build();
		}
		
		return Response.status(Status.UNAUTHORIZED).build();
	}
}
