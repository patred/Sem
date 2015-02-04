package it.synclab.patred.sem.webcontrollers.timesheet;

import it.synclab.patred.sem.annotations.Transactional;
import it.synclab.patred.sem.webcontrollers.BaseWebController;

import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.spi.resource.PerRequest;

@Transactional
@PerRequest
@Path("services/timesheet")
public class TimesheetController extends BaseWebController {
	
	@Context
	UriInfo uriInfo;
	
	@Context
	HttpServletRequest request;
	
	private static final String STATIC_PATH = "/it/synclab/patred/sem/webcontrollers/timesheet";
	
	@Inject
	public TimesheetController() {
		
	}
	
	@GET
	@Produces("text/html; charset=utf-8")
	public Viewable getTimesheet() {
		HashMap<String, Object> model = createParameterBean();
		
		return new Viewable("index.jsp", model);
	}
	
	@GET
	@Path("history")
	@Produces("text/html; charset=utf-8")
	public Viewable getTimesheetHistory() {
		HashMap<String, Object> model = createParameterBean();
		
		return new Viewable("index.jsp", model);
	}
	
	HashMap<String, Object> createParameterBean() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("staticpath", request.getContextPath() + STATIC_PATH);
		params.put("homepath", request.getContextPath());
		return params;
	}
	
}