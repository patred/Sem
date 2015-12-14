package it.synclab.patred.sem.webcontrollers.timesheet;

import it.synclab.patred.sem.annotations.AuthenticatedUser;
import it.synclab.patred.sem.annotations.MustAuthenticate;
import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.persistence.entities.Employee;
import it.synclab.patred.sem.persistence.entities.Timesheet;
import it.synclab.patred.sem.persistence.entities.TimesheetDetail;
import it.synclab.patred.sem.persistence.entities.User;
import it.synclab.patred.sem.services.persistent.EmployeeService;
import it.synclab.patred.sem.services.persistent.TimesheetDetailService;
import it.synclab.patred.sem.services.persistent.TimesheetService;
import it.synclab.patred.sem.webcontrollers.BaseWebController;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.spi.resource.PerRequest;

@Transactional
@PerRequest
@Path("services/timesheet")
public class TimesheetController extends BaseWebController {
	
	private static final String STATIC_PATH = "/it/synclab/patred/sem/webcontrollers/timesheet";
	
	@AuthenticatedUser
	private User user;
	
	@Inject
	private EmployeeService employeeService;
	
	@Inject
	private TimesheetService timesheetService;
	
	@Inject
	private TimesheetDetailService timesheetDetailService;
	
	@Context
	HttpServletRequest request;
	
	@Inject
	public TimesheetController() {
		
	}
	
	@GET
	@MustAuthenticate
	@Produces("text/html; charset=utf-8")
	public Viewable getTimesheet() {
		HashMap<String, Object> model = createParameterBean();
		
		Employee employee = employeeService.getByUser(user);
		List<Timesheet> timesheets = timesheetService.getByEmployee(employee);
		model.put("timesheets", timesheets);
		return new Viewable("timesheet.jsp", model);
	}
	
	@GET
	@MustAuthenticate
	@Path("detail/{id}")
	@Produces("text/html; charset=utf-8")
	public Viewable getTimesheetDetail(@PathParam("id") Long id) {
		HashMap<String, Object> model = createParameterBean();
		Timesheet timesheet = timesheetService.get(id);
		List<TimesheetDetail> timesheetDetails = timesheetDetailService.getTimesheetDetailByTimesheet(timesheet);
		logger.info("Trovati {} Dettagli", timesheetDetails.size());
		model.put("tDetails", timesheetDetails);
		return new Viewable("timesheetDetail.jsp", model);
	}
	
	@GET
	@MustAuthenticate
	@Path("detail/form")
	@Produces("text/html; charset=utf-8")
	public Viewable getTimesheetDetailForm() {
		HashMap<String, Object> model = createParameterBean();
		return new Viewable("detail.jsp", model);
	}
	
	@GET
	@MustAuthenticate
	@Path("form")
	@Produces("text/html; charset=utf-8")
	public Viewable getTimesheetForm() {
		HashMap<String, Object> model = createParameterBean();
		model.put("timesheet", new Timesheet());
		return new Viewable("timesheetForm.jsp", model);
	}
	
	@GET
	@MustAuthenticate
	@Path("form/{id}")
	@Produces("text/html; charset=utf-8")
	public Viewable getSingleTimesheet(@PathParam("id") Long id) {
		HashMap<String, Object> model = createParameterBean();
		Timesheet timesheet = timesheetService.get(id);
		model.put("timesheet", timesheet);
		return new Viewable("timesheetForm.jsp", model);
	}
	
	@POST
	@MustAuthenticate
	@Path("form")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTimesheetForm(Timesheet timesheet) {
		if(timesheet == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		logger.info("Before: " + timesheet);
		Employee employee = employeeService.getByUser(user);
		if (employee == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		timesheet.setEmployee(employee);
		logger.info("After: " + timesheet);
		timesheetService.save(timesheet);
		return Response.ok().build();
	}
	
	@POST
	@MustAuthenticate
	@Path("detail")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTimesheetDetailForm(TimesheetDetail timesheetDetail) {
		if (timesheetDetail == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		logger.info("timesheetDetail: " + timesheetDetail);
		timesheetDetailService.save(timesheetDetail);
		return Response.ok().build();
	}
	
	@PUT
	@MustAuthenticate
	@Path("form")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editTimesheetForm(Timesheet timesheet) {
		if (timesheet == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		logger.info("Before: " + timesheet);
		Timesheet temp = timesheetService.get(timesheet.getId());
		if (temp == null)
			return Response.status(Status.BAD_REQUEST).build();
		temp.update(timesheet);
		timesheetService.update(temp);
		logger.info("After: " + timesheet);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("form/{id}")
	public Object delete(@PathParam("id") Long id) {
		
		Timesheet timesheet = timesheetService.get(id);
		
		if (timesheet == null)
			return Response.status(Status.NOT_FOUND).build();
		
		timesheetService.delete(timesheet);
		return Response.ok().build();
	}
	
	@GET
	@MustAuthenticate
	@Path("history")
	@Produces("text/html; charset=utf-8")
	public Viewable getTimesheetHistory() {
		HashMap<String, Object> model = createParameterBean();
		
		return new Viewable("history.jsp", model);
	}
	
	HashMap<String, Object> createParameterBean() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("staticpath", request.getContextPath() + STATIC_PATH);
		params.put("homepath", request.getContextPath());
		return params;
	}
	
}