package it.synclab.patred.modules;

import com.google.inject.AbstractModule;

public class UserModule extends AbstractModule {

	@Override
	protected void configure() {
		System.out.println("configure...UserModule extends AbstractModule");

	}

}
