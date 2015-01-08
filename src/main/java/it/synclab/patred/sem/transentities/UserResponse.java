package it.synclab.patred.sem.transentities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "root")
public class UserResponse {
	
	public String user;
	public String role;
	
	public UserResponse() {
	}
	
	public UserResponse(String user) {
		super();
		this.user = user;
	}
	
}
