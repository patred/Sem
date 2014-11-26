package it.synclab.patred.listener;

import it.synclab.patred.aop.LogInterceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;

public class ApplicationListener extends GuiceServletContextListener {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	protected Injector getInjector() {
		logger.info("getInjector...");
		
		return Guice.createInjector(new JerseyServletModule() {
			
			@Override
			protected void configureServlets() {
				super.configureServlets();
				Map<String, String> params = new HashMap<String, String>();
				
				params.put("com.sun.jersey.config.feature.ImplicitViewables", "false");
				params.put("com.sun.jersey.config.feature.Redirect", "true");
				params.put("com.sun.jersey.config.property.packages", "it.synclab.patred.controllers");
				params.put(ServletContainer.PROPERTY_WEB_PAGE_CONTENT_REGEX, ".*\\.(htm|html|css|js|jsp|png|jpeg|jpg|gif)$");
				params.put("com.sun.jersey.spi.container.ResourceFilters", "com.sun.jersey.api.container.filter.RolesAllowedResourceFilterFactory");
				
				filter("/*").through(GuiceContainer.class, params);
			    
				install(new Module() {
					
					@Override
					public void configure(Binder binder) {
						LogInterceptor logInterceptor = new LogInterceptor();
						requestInjection(logInterceptor);
						
						bindInterceptor(Matchers.any(), Matchers.any(), logInterceptor);
						
					}
				});
			}
		});
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		logger.info("contextDestroyed...");
		super.contextDestroyed(servletContextEvent);
	}
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.info("contextInitialized...");
		super.contextInitialized(servletContextEvent);
	}
	
}
