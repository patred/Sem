package it.synclab.patred.controllers;

import it.synclab.patred.persistence.User;
import it.synclab.patred.services.UserService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.spi.resource.PerRequest;

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
	public List<it.synclab.patred.model.User> getAllJson() {
		
		return getAll();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<it.synclab.patred.model.User> getAll() {
		
		List<it.synclab.patred.model.User> users = new ArrayList<it.synclab.patred.model.User>();
		
		for (User stuff : userservice.getAll()) {
			users.add(new it.synclab.patred.model.User(stuff));
		}
		
		return users;
	}
	
	@GET
	@Path("json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public it.synclab.patred.model.User getByIdJson(@PathParam("id") String id) {
		
		return getById(id);
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML)
	public it.synclab.patred.model.User getById(@PathParam("id") String id) {
		User stuff = userservice.getUserFromUsername(id);
		if (stuff == null)
			return new it.synclab.patred.model.User();
		it.synclab.patred.model.User user = new it.synclab.patred.model.User(stuff);
		return user;
	}
	
}