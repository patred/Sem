package it.synclab.patred.rest.backoffice;

import it.synclab.patred.annotations.Transactional;
import it.synclab.patred.persistence.entities.Roles;
import it.synclab.patred.persistence.entities.User;
import it.synclab.patred.services.persistent.UserService;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.common.base.Strings;
import com.sun.jersey.spi.resource.PerRequest;

@Transactional
@PerRequest
@Path("backoffice/user")
public class UserController extends BaseBackofficeController {
	
	@Inject
	private UserService userservice;
	
	@Inject
	public UserController() {
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<User> getAll() {
		return userservice.getAll();
	}
	
	@GET
	@Path("{role}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getAllByRoleUser(@PathParam("role") String sRole) {
		
		Roles role = Roles.Empty;
		
		if (sRole.equalsIgnoreCase(Roles.Employee.name()))
			role = Roles.Employee;
		
		if (sRole.equalsIgnoreCase(Roles.Manager.name()))
			role = Roles.Manager;
		if (role == Roles.Empty)
			return Response.status(Status.BAD_REQUEST).build();
		
		User allByRoleUser = userservice.getAllByRoleUser(role);
		if (allByRoleUser != null)
			return Response.ok(allByRoleUser).build();
		return Response.status(Status.NOT_FOUND).build();
		
	}
	
	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_XML)
	public Response search(@QueryParam("userid") String userid) {
		if (Strings.isNullOrEmpty(userid))
			return Response.status(Status.BAD_REQUEST).build();
		User user = userservice.getByUsernameUser(userid);
		if (user != null)
			return Response.ok(user).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response save(User user) {
		userservice.save(user);
		return Response.ok().build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(User user) {
		
		userservice.update(user);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("{username}")
	public Object delete(@PathParam("username") String username) {
		User user = userservice.getByUsernameUser(username);
		
		if (user == null)
			return Response.status(Status.NOT_FOUND).build();
		userservice.delete(user);
		return Response.ok().build();
	}
}