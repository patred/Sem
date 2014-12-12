package it.synclab.patred.sem.webcontrollers;

import it.synclab.patred.sem.rest.BaseController;

import java.util.Hashtable;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.spi.resource.PerRequest;

@PerRequest
@Path("services/home")
public class HomePage extends BaseController {
	
	@Inject
	public HomePage() {
		
	}
	
	@GET
	@Produces("text/html; charset=utf-8")
	public Viewable getHomePage() {
		Hashtable<String, String> model = new Hashtable<String, String>();
		
		return new Viewable("/index.jsp", model);
	}
	
}