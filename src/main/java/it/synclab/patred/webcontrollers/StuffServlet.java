package it.synclab.patred.webcontrollers;

import it.synclab.patred.annotations.Transactional;
import it.synclab.patred.persistence.entities.User;
import it.synclab.patred.services.persistent.UserService;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.spi.resource.PerRequest;

@Transactional
@PerRequest
@Path("services/stuff")
public class StuffServlet extends BaseController {
	
	@Inject
	private UserService userservice;
	
	@Inject
	public StuffServlet() {
	}
	
	@GET
	@Path("json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllJson() {
		
		return getAll();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<User> getAll() {
		return userservice.getAll();
	}
	
	@GET
	@Path("json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getByIdJson(@PathParam("id") String id) {
		
		return getById(id);
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML)
	public User getById(@PathParam("id") String id) {
		User user = userservice.getByUsernameUser(id);
		if (user == null)
			return new User();
		//Hibernate.initialize(user.getEmployee());
		//Hibernate.initialize(user.getManager());
		return user;
	}
	
	@GET
	@Path("login/{id}/{password}")
	@Produces(MediaType.TEXT_HTML)
	public String login(@PathParam("id") String id, @PathParam("password") String password) {
		
		return String.valueOf(userservice.authenticate(id, password));
	}
	
}