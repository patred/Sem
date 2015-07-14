package it.synclab.patred.sem.ws;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.bind.annotation.XmlElement;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
@HandlerChain(file="handler-chain.xml")
public interface ESBInterfaceEmployee {

	@WebMethod
	public void getEmployee(@XmlElement(required = true) @WebParam(name = "EmployeeId") Long employeeId);

}