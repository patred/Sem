package it.synclab.patred.sem.rest.backoffice;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Roles;
import it.synclab.patred.sem.persistence.entities.User;
import it.synclab.patred.sem.services.persistent.EmployeeService;
import it.synclab.patred.sem.services.persistent.ManagerService;
import it.synclab.patred.sem.services.persistent.UserService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
import javax.ws.rs.core.GenericEntity;
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
	private ManagerService managerService;
	
	@Inject
	private EmployeeService employeeService;
	
	@Inject
	public UserController() {
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<User> getAll() {
		return userservice.getAll();
	}
	
	@GET
	@Path("new")
	@Produces(MediaType.APPLICATION_XML)
	public Object newObject() {
		return new User();
	}
	
	@GET
	@Path("{username}")
	@Produces(MediaType.TEXT_XML)
	public Object get(@PathParam("username") String username) {
		
		User user = userservice.getByUsernameUser(username);
		if (user != null)
			return user;
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public Response getAllByRoleUser(@QueryParam("role") String sRole) {
		
		Roles role = Roles.Empty;
		
		if (sRole.equalsIgnoreCase(Roles.Employee.name()))
			role = Roles.Employee;
		
		if (sRole.equalsIgnoreCase(Roles.Manager.name()))
			role = Roles.Manager;
		
		if (role == Roles.Empty)
			return Response.status(Status.BAD_REQUEST).build();
		
		List<User> allByRoleUsers = userservice.getAllByRoleUser(role);
		
		if (allByRoleUsers.size() > 0) {
			try {
				GenericEntity<List<User>> entity = new GenericEntity<List<User>>(allByRoleUsers) {
				};
				return Response.ok(entity).build();
			} catch (Exception e) {
				logger.error("Grave internal error!", e);
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		
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
		if (user == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		switch (user.getRole()) {
			case Employee:
				employeeService.save(user.getEmployee());
				break;
			case Manager:
				managerService.save(user.getManager());
				break;
			default:
				break;
		}
		
		try {
			user.setPassword(user.getUsername());
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			logger.warn(e.getMessage());
			return Response.serverError().build();
		}
		userservice.save(user);
		return Response.ok().build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(User user) {
		if (user == null)
			return Response.status(Status.BAD_REQUEST).build();

		switch (user.getRole()) {
			case Employee:
				employeeService.update(user.getEmployee());
				break;
			case Manager:
				managerService.update(user.getManager());
				break;
			default:
				break;
		}
		userservice.update(user);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("{username}")
	public Object delete(@PathParam("username") String username) {
		User user = userservice.getByUsernameUser(username);
		
		if (user == null)
			return Response.status(Status.NOT_FOUND).build();
		
		switch (user.getRole()) {
			case Employee:
				employeeService.delete(user.getEmployee());
				break;
			case Manager:
				managerService.delete(user.getManager());
				break;
			default:
				break;
		}
		
		userservice.delete(user);
		return Response.ok().build();
	}
}