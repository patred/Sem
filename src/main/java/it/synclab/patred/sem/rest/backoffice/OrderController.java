package it.synclab.patred.sem.rest.backoffice;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Order;
import it.synclab.patred.sem.services.persistent.OrderService;

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
@Path("backoffice/order")
public class OrderController extends BaseBackofficeController {
	
	@Inject
	private OrderService orderService;
	
	@Inject
	public OrderController() {
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Order> getAll() {
		return orderService.getAll();
	}
	
	@GET
	@Path("new")
	@Produces(MediaType.APPLICATION_XML)
	public Object newObject() {
		return new Order();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.TEXT_XML)
	public Object get(@PathParam("id") Long id) {
		
		Order order = orderService.get(id);
		if (order != null)
			return order;
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
	public Response save(Order order) {
		if(order == null || order.getClient() == null || order.getClient().getCompanyName() == null || "".equals(order.getClient().getCompanyName().trim()))
			return Response.status(Status.BAD_REQUEST).build();
		orderService.save(order);
		return Response.ok().build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(Order order) {
		if (order == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		orderService.update(order);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("{id}")
	public Object delete(@PathParam("id") Long id) {
		Order order = orderService.get(id);
		
		if (order == null)
			return Response.status(Status.NOT_FOUND).build();
		
		orderService.delete(order);
		return Response.ok().build();
	}
}