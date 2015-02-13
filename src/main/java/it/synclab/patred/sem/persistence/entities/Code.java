package it.synclab.patred.sem.persistence.entities;

public enum Code {
	TR("Trasferta"), SD("Sede Disagiata");
	private String description;
	
	private Code(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
