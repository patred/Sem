package it.synclab.patred.sem.rest.backoffice;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Client;
import it.synclab.patred.sem.services.persistent.ClientService;

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
@Path("backoffice/client")
public class ClientController extends BaseBackofficeController {
	
	@Inject
	private ClientService clientService;
	
	@Inject
	public ClientController() {
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Client> getAll() {
		return clientService.getAll();
	}
	
	@GET
	@Path("new")
	@Produces(MediaType.APPLICATION_XML)
	public Object newObject() {
		return new Client();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.TEXT_XML)
	public Object get(@PathParam("id") Long id) {
		
		Client client = clientService.get(id);
		if (client != null)
			return client;
		return Response.status(Status.NOT_FOUND).build();
	}
	
	/**
	 * TODO
	 * 
	 * @param companyName
	 * @return
	 */
	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_XML)
	public Response search(@QueryParam("name") String companyName) {
		if (Strings.isNullOrEmpty(companyName))
			return Response.status(Status.BAD_REQUEST).build();
		logger.info(companyName);
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response save(Client client) {
		if (client == null)
			return Response.status(Status.BAD_REQUEST).build();
		clientService.save(client);
		return Response.ok().build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(Client client) {
		if (client == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		clientService.update(client);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("{id}")
	public Object delete(@PathParam("id") Long id) {
		Client client = clientService.get(id);
		
		if (client == null)
			return Response.status(Status.NOT_FOUND).build();
		
		clientService.delete(client);
		return Response.ok().build();
	}
}