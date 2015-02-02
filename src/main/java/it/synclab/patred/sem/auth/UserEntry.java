package it.synclab.patred.sem.auth;

import java.io.Serializable;

public class UserEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	private String token;
	private String userid;

	
	public UserEntry(String userid, String token) {
		this.userid = userid;
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public String getUserid() {
		return userid;
	}

	@Override
	public String toString() {
		return "UserEntry [token=" + token + ", userid=" + userid + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserEntry other = (UserEntry) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

}
