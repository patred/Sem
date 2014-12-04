package it.synclab.patred.listener;

import it.synclab.patred.annotations.NoTransactional;
import it.synclab.patred.annotations.Transactional;
import it.synclab.patred.aop.LogInterceptor;
import it.synclab.patred.aop.TransactionInterceptor;
import it.synclab.patred.aop.TrimAndNullInterceptor;
import it.synclab.patred.boot.Constants;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.GuiceServletContextListener;
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
				params.put("com.sun.jersey.config.property.packages", "it.synclab.patred");
				params.put(ServletContainer.PROPERTY_WEB_PAGE_CONTENT_REGEX, ".*\\.(htm|html|css|js|jsp|png|jpeg|jpg|gif)$");
				params.put("com.sun.jersey.spi.container.ResourceFilters", "com.sun.jersey.api.container.filter.RolesAllowedResourceFilterFactory");
				
				filter("/*").through(GuiceContainer.class, params);
				
				install(new Module() {
					
					@Override
					public void configure(Binder binder) {
						LogInterceptor logInterceptor = new LogInterceptor();
						TrimAndNullInterceptor trimAndNullInterceptor = new TrimAndNullInterceptor();
						bindInterceptor(
								Matchers.annotatedWith(Path.class),
								Matchers.annotatedWith(GET.class).or(Matchers.annotatedWith(POST.class)).or(Matchers.annotatedWith(PUT.class))
										.or(Matchers.annotatedWith(DELETE.class)), trimAndNullInterceptor, logInterceptor);
						
						TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
						requestInjection(transactionInterceptor);
						bindInterceptor(Matchers.annotatedWith(Transactional.class), Matchers.not(Matchers.annotatedWith(NoTransactional.class)), transactionInterceptor);
						
					}
				});
				
			}
		});
		
	}
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.info("contextInitialized...");
		super.contextInitialized(servletContextEvent);
		
		Injector injector = getInjector();
		Constants constant = new Constants(injector);
		
		changeContext(constant);
		
	}
	
	public void changeContext(Constants constant) {
		try {
			InitialContext ic = new InitialContext();
			Context xmlContext = (Context) ic.lookup("java:comp/env");
			
			Object lookup = xmlContext.lookup("jdbc/confluence");
			logger.info("lookup: " + lookup.getClass().getCanonicalName());
			
			logger.info("{}:{}:{}", new Object[] { constant.getUrlDB(), constant.getUserDB(), constant.getPasswordDB() });
			
			BasicDataSource bds = null;
			DataSource ds = null;
			// if (lookup instanceof BasicDataSource) {
			// bds = (BasicDataSource) lookup;
			// logger.info("{}:{}:{}", new Object[] { bds.getUrl(),
			// bds.getUsername(), bds.getPassword() });
			// } else {
			// ds = (DataSource) lookup;
			// logger.info("{}:{}:{}", new Object[] { ((BasicDataSource)
			// ds).getUrl(), ((BasicDataSource) ds).getUsername(),
			// ((BasicDataSource) ds).getPassword() });
			// }
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		logger.info("contextDestroyed...");
		super.contextDestroyed(servletContextEvent);
		
	}
}
