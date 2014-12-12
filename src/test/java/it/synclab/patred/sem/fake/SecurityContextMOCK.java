package it.synclab.patred.sem.fake;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class SecurityContextMOCK implements SecurityContext {
	
	public SecurityContextMOCK() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Principal getUserPrincipal() {
		Principal principal = new Principal() {
			
			@Override
			public String getName() {
				return "name";
			}
		};
		return principal;
	}
	
	@Override
	public boolean isUserInRole(String role) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String getAuthenticationScheme() {
		// TODO Auto-generated method stub
		return null;
	}
	
}