package it.synclab.patred.boot;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class Constants {
	
	private Injector injector;
	
	@Inject
	public Constants(Injector injector) {
		this.injector = injector;
	}
	
	public Injector getInjector() {
		return injector;
	}
	
	public void setInjector(Injector injector) {
		this.injector = injector;
	}
	
}
