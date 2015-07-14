package it.synclab.patred.sem.ws.handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingSOAPHandler implements SOAPHandler<SOAPMessageContext> {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public LoggingSOAPHandler() {
	}
	
	@Override
	public void close(MessageContext context) {
	}
	
	@Override
	public boolean handleFault(SOAPMessageContext context) {
		Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		logMessage(context.getMessage(), isRequest);
		return true;
	}
	
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		logMessage(context.getMessage(), isRequest);
		return true;
	}
	
	@Override
	public Set<QName> getHeaders() {
		return null;
	}
	
	private void logMessage(SOAPMessage message, boolean isRequest) {
		
		StringBuilder buffer = new StringBuilder();
		
		if (isRequest) {
			buffer.append("Outbound:\n");
		} else {
			buffer.append("Inbound:\n");
		}
		
		try {
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			message.writeTo(out);
			buffer.append(out.toString());
			logger.info(buffer.toString());
			out.close();
			
		} catch (SOAPException | IOException e) {
			logger.debug("Error when logging soap message", e);
		}
		
	}
}
