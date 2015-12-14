package it.synclab.patred.sem.webcontrollers;

import it.synclab.patred.sem.rest.BaseController;

public class BaseWebController extends BaseController {
	
	public BaseWebController() {
		
	}
	
	public boolean isSuccessfulLogged(String userid, String token) {
		
		if (userid == null || token == null)
			return false;
		
		if (userTokenService.get(userid) == null || userTokenService.get(userid).getToken() == null)
			return false;
		
		return userTokenService.get(userid).getToken().equals(token);
	}
	
}
