package it.synclab.patred.sem.persistence.entities;

import it.synclab.patred.sem.util.PasswordEncryption;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@XmlRootElement
@NamedQueries(
		{	@NamedQuery(name = "getAllUser", query = "select u from User u"),
			@NamedQuery(name = "getAllByRoleUser", query = "select u from User u where u.role = :role order by u.username asc"),
			@NamedQuery(name = "getByUsernameUser", query = "select u from User u where u.username = :username"),
			@NamedQuery(name = "getAvailableByRoleUser", query = "select u from User u where u.username not in (:usernames) and u.role = :role"),
			@NamedQuery(name = "deleteAllUser", query = "delete from User u")
		})
public class User implements Serializable {
	private static final long serialVersionUID = -3341290174467662162L;
	
	private String username;
	private byte[] password;
	private byte[] salt;
	private Roles role;
	
	public User() {
		this.role = Roles.Empty;
	}
	
	public User(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		this.username = username;
		this.salt = PasswordEncryption.generateSalt();
		this.password = PasswordEncryption.getEncryptedPassword(password, salt);
		this.role = Roles.Empty;
		
	}
	
	public User(String username, String password, Roles role) throws NoSuchAlgorithmException, InvalidKeySpecException {
		this.username = username;
		this.salt = PasswordEncryption.generateSalt();
		this.password = PasswordEncryption.getEncryptedPassword(password, salt);
		this.role = role;
		
	}
	
	@Id
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Roles getRole() {
		return role;
	}
	
	public void setRole(Roles role) {
		this.role = role;
	}
	
	public byte[] getSalt() {
		return salt;
	}
	
	public void setSalt() throws NoSuchAlgorithmException {
		this.salt = PasswordEncryption.generateSalt();
	}
	
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	
	public void setPassword(byte[] password) {
		this.password = password;
	}
	
	public byte[] getPassword() {
		return password;
	}
	
	public void setPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		this.salt = PasswordEncryption.generateSalt();
		this.password = PasswordEncryption.getEncryptedPassword(password, salt);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", role=" + role + "]";
	}
	
}
