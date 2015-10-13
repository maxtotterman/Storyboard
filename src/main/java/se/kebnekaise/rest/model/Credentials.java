package se.kebnekaise.rest.model;

import java.io.Serializable;

public class Credentials implements Serializable
{
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}