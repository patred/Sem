package it.synclab.patred.sem;

import it.synclab.patred.sem.fake.CalendarServiceMOCK;
import it.synclab.patred.sem.fake.SecurityContextMOCK;
import it.synclab.patred.sem.fake.UriInfoMOCK;
import it.synclab.patred.sem.services.CalendarService;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.google.inject.Binder;
import com.google.inject.Module;

public class TModule implements Module {
	
	public void configure(Binder binder) {
		binder.bind(CalendarService.class).to(CalendarServiceMOCK.class);
		binder.bind(SecurityContext.class).to(SecurityContextMOCK.class);
		binder.bind(UriInfo.class).to(UriInfoMOCK.class);
		
	}
	
}