package it.synclab.patred.sem.rest.backoffice;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Employee;
import it.synclab.patred.sem.services.persistent.EmployeeService;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.spi.resource.PerRequest;

@Transactional
@PerRequest
@Path("backoffice/employee")
public class EmployeeController extends BaseBackofficeController {
	
	@Inject
	private EmployeeService employeeService;
	
	@Inject
	public EmployeeController() {
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Employee> getAll() {
		return employeeService.getAll();
	}
	
	@GET
	@Path("new")
	@Produces(MediaType.APPLICATION_XML)
	public Object newObject() {
		return new Employee();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.TEXT_XML)
	public Object get(@PathParam("id") Long id) {
		Employee employee = employeeService.get(id);
		if (employee == null)
			return Response.status(Status.NOT_FOUND).build();
		return employee;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response save(Employee employee) {
		if (employee == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		employeeService.save(employee);
		
		return Response.ok().build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(Employee employee) {
		if (employee == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		employeeService.update(employee);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("{id}")
	public Object delete(@PathParam("id") Long id) {
		Employee employee = employeeService.get(id);
		
		if (employee == null)
			return Response.status(Status.NOT_FOUND).build();
		
		employeeService.delete(employee);
		return Response.ok().build();
	}
}