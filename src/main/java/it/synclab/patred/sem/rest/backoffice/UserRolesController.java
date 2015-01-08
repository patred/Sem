package it.synclab.patred.sem.rest.backoffice;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.User;
import it.synclab.patred.sem.services.persistent.UserService;
import it.synclab.patred.sem.transentities.UserResponse;

import javax.ws.rs.FormParam;
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
@Path("backoffice/userroles")
public class UserRolesController extends BaseBackofficeController {
	
	@Inject
	private UserService userservice;
	
	@Inject
	public UserRolesController() {
	}
	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	public Object getMyRoles(@FormParam("username") String username, @FormParam("password") String password) {
		UserResponse response = new UserResponse();
		
		boolean authenticate = userservice.authenticate(username, password);
		if (!authenticate)
			return Response.status(Status.UNAUTHORIZED).build();
		User user = userservice.getByUsernameUser(username);
		response.user = user.getUsername();
		response.role = user.getRole().name();
		
		return response;
	}
}
