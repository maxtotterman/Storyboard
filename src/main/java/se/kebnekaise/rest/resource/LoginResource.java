package se.kebnekaise.rest.resource;

import se.kebnekaise.java.spring.service.AuthBean;
import se.kebnekaise.rest.model.Credentials;
import se.kebnekaise.rest.model.Token;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/login")
public final class LoginResource
{
	@Inject
	AuthBean bean;

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public Response authenticateUser(Credentials credentials) {
		final String username = credentials.getUsername();
		final String password = credentials.getPassword();
		try {
			// Authenticate the user using the credentials provided
			authenticate(username, password);
			// Issue a token for the user
			Token token = issueToken(username);
			// Return the token on the response
			return Response.ok(token).build();
		}
		catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	private void authenticate(String username, String password) throws Exception {
		// Authenticate against a database, LDAP, file or whatever
		// Throw an Exception if the credentials are invalid
		bean.AuthLogin(username, password);
	}

	private Token issueToken(String username) {
		// Issue a token (can be a random String persisted to a database or a JWT token)
		// The issued token must be associated to a user
		// Return the issued token
		Token token = new Token(bean.createToken(username));
		return token;
	}
}

