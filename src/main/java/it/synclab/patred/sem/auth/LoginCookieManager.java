package it.synclab.patred.sem.auth;

import it.synclab.patred.sem.services.cluster.UserTokenService;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class LoginCookieManager {
	
	private static final String LOGGIN_COOKIE_NAME = "logintoken";
	private static final Pattern RE_COOKIE = Pattern.compile("^(.*)-(.*)$");
	
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
		
		Matcher matcher = RE_COOKIE.matcher(loginCookieValue);
		if (matcher.matches()) {
			String userid = matcher.group(1);
			String token = matcher.group(2);
			UserEntry userEntry = userTokenService.get(userid);
			if (userEntry != null && userEntry.getToken() != null && userEntry.getToken().equals(token))
				return userEntry;
		}
		
		return null;
	}
}
