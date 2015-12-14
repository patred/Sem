package it.synclab.patred.sem.ws;

import javax.xml.ws.WebServiceFeature;

import com.sun.xml.ws.api.FeatureConstructor;

/**
 * The feature, just holds a unique ID and sets the enabled flag.
 * 
 * @author Marcus Eriksson, krummas@gmail.com
 * @since Nov 4, 2008
 */
public class GuiceManagedFeature extends WebServiceFeature {
	public static final String ID = "patred.guice.managed.feature";
	
	@FeatureConstructor
	public GuiceManagedFeature() {
		this.enabled = true;
	}
	
	public String getID() {
		return ID;
	}
}
