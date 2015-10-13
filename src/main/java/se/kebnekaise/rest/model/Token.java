package se.kebnekaise.rest.model;

public final class Token
{
	private String token;

	public Token(String token) {
		this.token = token;
	}
	protected Token(){}

	public String getToken() {
		return token;
	}
}
