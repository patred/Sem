package it.synclab.patred.sem.ws;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "it.synclab.patred.sem.ws.FaultBean")
public class ESBInterfaceException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private FaultBean faultBean;
	
	public ESBInterfaceException() {
		super();
	}
	
	public ESBInterfaceException(String message, FaultBean faultBean, Throwable cause) {
		super(message, cause);
		this.faultBean = faultBean;
	}
	
	public ESBInterfaceException(String message, FaultBean faultBean) {
		super(message);
		this.faultBean = faultBean;
	}
	
	public FaultBean getFaultInfo() {
		return faultBean;
	}
	
}
