package se.kebnekaise.rest.model;

public final class User
{
	private String firstname;
	private String surname;

	public User(String firstname, String surname) {
		this.firstname = firstname;
		this.surname = surname;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override public String toString() {
		return "User [firstname=" + firstname + ", surname=" + surname + "]";
	}
}
