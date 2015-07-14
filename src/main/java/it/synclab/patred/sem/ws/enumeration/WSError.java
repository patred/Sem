package it.synclab.patred.sem.ws.enumeration;

public enum WSError {
	
	Generic("-1", "Unknown Error");
	
	private final String code;
	private final String desc;
	
	WSError(final String code, final String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getDesc() {
		return desc;
	}
	
}
