package it.synclab.patred.sem.ws;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.server.ResourceInjector;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.api.server.WSWebServiceContext;
import com.sun.xml.ws.server.AbstractMultiInstanceResolver;

/**
 * The instance resolver
 * <p/>
 * Looks at the endpoint class and gets the annotation in order to know what
 * guice module to use when injecting the dependencies into the endpoint.
 * 
 * @author Marcus Eriksson, krummas@gmail.com
 * @since Nov 4, 2008
 */
@Singleton
public class GuiceManagedInstanceResolver<T> extends AbstractMultiInstanceResolver<T> {
	
	private static Injector injector;
	private ResourceInjector resourceInjector;
	private WSWebServiceContext webServiceContext;
	
	public GuiceManagedInstanceResolver(@NotNull final Class<T> clazz) throws IllegalAccessException, InstantiationException {
		super(clazz);
	}
	
	public static void setInjector(Injector injector) {
		GuiceManagedInstanceResolver.injector = injector;
	}
	
	/**
	 * Let guice create the instance
	 * <p/>
	 * the {@code create()} method in {@code AbstractMultiInstanceResolver}
	 * simply returns {@code clazz.newInstance()} so no magic happens there.
	 * <p/>
	 * If the endpoint is declared as singleton, the same instance will be
	 * returned every time.
	 * 
	 * @param packet
	 * @return
	 */
	@Override
	public T resolve(@NotNull final Packet packet) {
		final T instance = GuiceManagedInstanceResolver.injector.getInstance(this.clazz);
		this.resourceInjector.inject(this.webServiceContext, instance);
		return instance;
	}


	/**
	 * save the web service context instance
	 * 
	 * @param wsc
	 * @param endpoint
	 */
	@Override
	public void start(final WSWebServiceContext wsc, final WSEndpoint endpoint) {
		super.start(wsc, endpoint);
		this.resourceInjector = GuiceManagedInstanceResolver.getResourceInjector(endpoint);
		this.webServiceContext = wsc;
		
	}
}
