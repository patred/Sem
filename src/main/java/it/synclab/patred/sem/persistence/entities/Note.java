package it.synclab.patred.sem.persistence.entities;

public enum Note {
	P("Ferie"), P2("Mezzo Permesso"), M("Malattia"), F("Festività"), DS("Donazione Sangue"), CP("Congedo Parentale");
	private String description;
	
	private Note(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
