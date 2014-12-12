package it.synclab.patred.rest.backoffice;

import it.synclab.patred.webcontrollers.BaseController;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

public class BaseBackofficeController extends BaseController {
	@Context
	protected SecurityContext sc;
	
	public SecurityContext getSc() {
		return sc;
	}
	
	public void setSc(SecurityContext sc) {
		this.sc = sc;
	}
}
