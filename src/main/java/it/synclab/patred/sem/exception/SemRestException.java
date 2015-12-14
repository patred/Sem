package it.synclab.patred.sem.exception;

import it.synclab.patred.sem.util.LogLevel;

import javax.ws.rs.core.Response.Status;

public class SemRestException extends RuntimeException {
	
	private static final long serialVersionUID = 5588787294400524841L;
	
	private LogLevel logLevel = LogLevel.error;
	private Status status = Status.INTERNAL_SERVER_ERROR;
	private int errorcode = Status.INTERNAL_SERVER_ERROR.getStatusCode();
	
	public SemRestException(LogLevel logLevel, Status status, int errorcode) {
		super();
		this.logLevel = logLevel;
		this.status = status;
		this.errorcode = errorcode;
	}
	
	public SemRestException(String message, LogLevel logLevel, Status status, int errorcode) {
		super(message);
		this.logLevel = logLevel;
		this.status = status;
		this.errorcode = errorcode;
	}
	
	public SemRestException(Throwable cause, LogLevel logLevel, Status status, int errorcode) {
		super(cause);
		this.logLevel = logLevel;
		this.status = status;
		this.errorcode = errorcode;
	}
	
	public SemRestException(String message, Throwable cause, LogLevel logLevel, Status status, int errorcode) {
		super(message, cause);
		this.logLevel = logLevel;
		this.status = status;
		this.errorcode = errorcode;
	}
	
	public SemRestException(String message) {
		super(message);
	}
	
	public SemRestException(Throwable cause) {
		super(cause);
	}
	
	public SemRestException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public LogLevel getLogLevel() {
		return logLevel;
	}
	
	public SemRestException setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
		return this;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public SemRestException setStatus(Status status) {
		this.status = status;
		return this;
	}
	
	public int getErrorcode() {
		return errorcode;
	}
	
	public SemRestException setErrorcode(int errorcode) {
		this.errorcode = errorcode;
		return this;
	}
	
}
