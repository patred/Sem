package it.synclab.patred.sem.ws;

import it.synclab.patred.sem.ws.enumeration.WSError;

public class FaultBean {
	
	public String faultCode;
	public String faultDescription;
	public String faultDetail;
	
	public FaultBean() {
		faultCode = "-1";
		faultDescription = "Unknown Error";
	}
	
	public FaultBean(WSError crmError) {
		this.faultCode = crmError.getCode();
		this.faultDescription = crmError.getDesc();
	}
	
	public FaultBean(String faultCode, String faultDescription) {
		this.faultCode = faultCode;
		this.faultDescription = faultDescription;
	}
	
}
