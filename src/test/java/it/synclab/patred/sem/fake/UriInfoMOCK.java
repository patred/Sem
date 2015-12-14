package it.synclab.patred.sem.fake;

import it.synclab.patred.sem.util.MetadataMap;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.api.uri.UriBuilderImpl;

public class UriInfoMOCK implements UriInfo {
	MultivaluedMap<String, String> queryParams = new MetadataMap<String, String>();
	public String path;
	public List<PathSegment> segments;
	public URI requesturi;
	public URI uri;
	public MultivaluedMap<String, String> pathparameters;
	public List<String> matchedUri;
	public List<Object> matchedResource;
	public UriBuilderImpl uriBuilder = new UriBuilderImpl();
	
	@Override
	public URI getAbsolutePath() {
		return uri;
	}
	
	@Override
	public UriBuilder getAbsolutePathBuilder() {
		return uriBuilder;
	}
	
	@Override
	public URI getBaseUri() {
		return uri;
	}
	
	@Override
	public UriBuilder getBaseUriBuilder() {
		return uriBuilder;
	}
	
	@Override
	public List<Object> getMatchedResources() {
		return matchedResource;
	}
	
	@Override
	public List<String> getMatchedURIs() {
		return matchedUri;
	}
	
	@Override
	public List<String> getMatchedURIs(boolean arg0) {
		return matchedUri;
	}
	
	@Override
	public String getPath() {
		return path;
	}
	
	@Override
	public String getPath(boolean arg0) {
		return path;
	}
	
	@Override
	public MultivaluedMap<String, String> getPathParameters() {
		return pathparameters;
	}
	
	@Override
	public MultivaluedMap<String, String> getPathParameters(boolean arg0) {
		return pathparameters;
	}
	
	@Override
	public List<PathSegment> getPathSegments() {
		return segments;
	}
	
	@Override
	public List<PathSegment> getPathSegments(boolean arg0) {
		return segments;
	}
	
	@Override
	public MultivaluedMap<String, String> getQueryParameters() {
		return queryParams;
	}
	
	@Override
	public MultivaluedMap<String, String> getQueryParameters(boolean arg0) {
		return queryParams;
	}
	
	@Override
	public URI getRequestUri() {
		return requesturi;
	}
	
	@Override
	public UriBuilder getRequestUriBuilder() {
		return uriBuilder;
	}
}
