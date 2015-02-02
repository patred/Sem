package it.synclab.patred.sem.services.cluster;

import it.synclab.patred.sem.auth.UserEntry;
import it.synclab.patred.sem.services.InfinispanService;

import java.util.UUID;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserTokenService extends GenericClusterService<String, UserEntry> {
	
	@Inject
	public UserTokenService(InfinispanService infinispanService) {
		super(infinispanService);
	}
	
	@Override
	protected String getCacheName() {
		return "auth-token-cache";
	}
	
	public UserEntry put(String userid) {
		
		String token = UUID.randomUUID().toString();
		
		UserEntry userEntry = get(userid);
		if (userEntry != null)
			token = userEntry.getToken();
		
		return put(userid, token);
	}
	
	public UserEntry put(String userid, String token) {
		UserEntry entry = new UserEntry(userid, token);
		put(userid, entry);
		return entry;
	}
	
}
