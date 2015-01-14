package it.synclab.patred.sem.persistence.entities;

import it.synclab.patred.sem.util.PasswordEncryption;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "getAllUser", query = "select u from User u"),
		@NamedQuery(name = "getAllByRoleUser", query = "select u from User u where u.role = :role order by u.username asc"),
		@NamedQuery(name = "getByUsernameUser", query = "select u from User u where u.username = :username"), @NamedQuery(name = "deleteAllUser", query = "delete from User u") })
public class User implements Serializable {
	private static final long serialVersionUID = -3341290174467662162L;
	
	private String username;
	private byte[] password;
	private byte[] salt;
	private String name;
	private String surname;
	
	private Roles role;
	private Employee employee;
	private Manager manager;
	
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
	
	@OneToOne
	@JoinColumn(unique = true)
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@OneToOne
	@JoinColumn(unique = true)
	public Manager getManager() {
		return manager;
	}
	
	public void setManager(Manager manager) {
		this.manager = manager;
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setPassword(byte[] password) {
		this.password = password;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
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
		return "User [username=" + username + ", name=" + name + ", surname=" + surname + ", password=" + password + ", role=" + role + ", employee=" + employee + ", manager="
				+ manager + "]";
	}
	
}
