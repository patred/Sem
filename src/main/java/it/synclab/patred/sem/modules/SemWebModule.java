package it.synclab.patred.sem.modules;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;

public class SemWebModule extends ServletModule {
	
	@Override
	protected void configureServlets() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put(ResourceConfig.FEATURE_IMPLICIT_VIEWABLES, "false");
		params.put(ResourceConfig.FEATURE_REDIRECT, "true");
		params.put(PackagesResourceConfig.PROPERTY_PACKAGES, "it.synclab.patred.rest, it.synclab.patred.webcontrollers");
		params.put(ServletContainer.PROPERTY_WEB_PAGE_CONTENT_REGEX, ".*\\.(htm|html|css|js|jsp|png|jpeg|jpg|gif)$");
		params.put(ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES,
				"com.sun.jersey.api.container.filter.RolesAllowedResourceFilterFactory, it.synclab.patred.rest.filter.CacheFilterFactory");
		
		filter("/*").through(GuiceContainer.class, params);
		
		install(new SemModule());
		
	}
	
}
