package it.synclab.patred.sem.auth;

import it.synclab.patred.sem.services.cluster.UserTokenService;

import java.util.Date;
import java.util.Map;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class LoginCookieManager {
	
	private static final String LOGGIN_COOKIE_NAME = "logintoken";
	
	@Inject
	private UserTokenService userTokenService;
	
	public ResponseBuilder login(ResponseBuilder response, String userid, String token, Date validTo) {
		String cookievalue = userid + "-" + token;
		int maxAge = (int) (validTo.getTime() - new Date().getTime()) / 1000;
		
		return response.cookie(new NewCookie(LOGGIN_COOKIE_NAME, cookievalue, "/", null, null, maxAge, false));
	}
	
	public ResponseBuilder logout(ResponseBuilder response) {
		return response.cookie(new NewCookie(LOGGIN_COOKIE_NAME, "-1", "/", null, null, 0, false));
	}
	
	public UserEntry getUserEntryFromCookies(Map<String, javax.ws.rs.core.Cookie> cookies) {
		
		String loginCookieValue = "-1";
		if (cookies.containsKey(LOGGIN_COOKIE_NAME))
			loginCookieValue = cookies.get(LOGGIN_COOKIE_NAME).getValue();
		
		if (!"-1".equals(loginCookieValue)) {
			String userid = loginCookieValue.substring(0, loginCookieValue.indexOf("-"));
			String token = loginCookieValue.substring(loginCookieValue.indexOf("-") + 1);
			UserEntry userEntry = userTokenService.get(userid);
			if (userEntry != null && userEntry.getToken() != null && userEntry.getToken().equals(token))
				return userEntry;
		}
		
		return null;
	}
}
