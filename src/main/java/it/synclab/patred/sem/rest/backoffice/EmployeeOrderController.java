package it.synclab.patred.sem.rest.backoffice;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Employee;
import it.synclab.patred.sem.persistence.entities.EmployeeOrder;
import it.synclab.patred.sem.persistence.entities.Order;
import it.synclab.patred.sem.services.persistent.EmployeeOrderService;
import it.synclab.patred.sem.services.persistent.EmployeeService;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.spi.resource.PerRequest;

@Transactional
@PerRequest
@Path("backoffice/employeeorder")
public class EmployeeOrderController extends BaseBackofficeController {
	
	@Inject
	private EmployeeOrderService employeeOrderService;
	
	@Inject
	private EmployeeService employeeService;
	
	@Inject
	private OrderService orderService;
	
	@Inject
	public EmployeeOrderController() {
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<EmployeeOrder> getAll() {
		return employeeOrderService.getAll();
	}
	
	@GET
	@Path("new")
	@Produces(MediaType.APPLICATION_XML)
	public Object newObject() {
		return new EmployeeOrder();
	}
	
	@GET
	@Path("employee/{id}")
	@Produces(MediaType.TEXT_XML)
	public Response getByEmployeeId(@PathParam("id") Long id) {
		Employee employee = employeeService.get(id);
		if (employee == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		List<EmployeeOrder> employeeOrders = employeeOrderService.getByEmployee(employee);
		
		try {
			GenericEntity<List<EmployeeOrder>> entity = new GenericEntity<List<EmployeeOrder>>(employeeOrders) {
			};
			return Response.ok(entity).build();
		} catch (Exception e) {
			logger.error("Grave internal error!", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("order/{id}")
	@Produces(MediaType.TEXT_XML)
	public Response getByOrderId(@PathParam("id") Long id) {
		Order order = orderService.get(id);
		if (order == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		List<EmployeeOrder> employeeOrders = employeeOrderService.getByOrder(order);
		
		try {
			GenericEntity<List<EmployeeOrder>> entity = new GenericEntity<List<EmployeeOrder>>(employeeOrders) {
			};
			return Response.ok(entity).build();
		} catch (Exception e) {
			logger.error("Grave internal error!", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response save(EmployeeOrder employeeOrder) {
		if (employeeOrder == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		employeeOrderService.save(employeeOrder);
		
		return Response.ok().build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(EmployeeOrder employeeOrder) {
		if (employeeOrder == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		employeeOrderService.update(employeeOrder);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("employee/{id}")
	public Object deleteByEmployee(@PathParam("id") Long id) {
		
		Employee employee = employeeService.get(id);
		if (employee == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		List<EmployeeOrder> employeeOrders = employeeOrderService.getByEmployee(employee);
		
		if (employeeOrders == null)
			return Response.status(Status.NOT_FOUND).build();
		for (EmployeeOrder eo : employeeOrders) {
			employeeOrderService.delete(eo);
		}
		
		return Response.ok().build();
	}
	
	@DELETE
	@Path("order/{id}")
	public Object deleteByOrder(@PathParam("id") Long id) {
		Order order = orderService.get(id);
		if (order == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		List<EmployeeOrder> employeeOrders = employeeOrderService.getByOrder(order);
		
		if (employeeOrders == null)
			return Response.status(Status.NOT_FOUND).build();
		
		for (EmployeeOrder eo : employeeOrders) {
			employeeOrderService.delete(eo);
		}
		return Response.ok().build();
	}
}