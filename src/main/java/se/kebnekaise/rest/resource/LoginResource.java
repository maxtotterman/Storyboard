package se.kebnekaise.rest.resource;

import se.kebnekaise.java.spring.service.AuthBean;
import se.kebnekaise.rest.model.Credentials;
import se.kebnekaise.rest.model.Token;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/login")
@Produces("application/json")
@Consumes("application/json")
public final class LoginResource
{
	@Inject
	AuthBean bean;

	@POST
	public Response authenticateUser(Credentials credentials) {
		final String username = credentials.getUsername();
		final String password = credentials.getPassword();

		if (username == null || username.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		if (password == null || password.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		try {
			if (authenticate(username, password)) {
				Token token = issueToken(username);
				return Response.ok(token).build();
			}
		}
		catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	private boolean authenticate(String username, String password) throws Exception {
		return bean.authLogin(username, password);
	}

	private Token issueToken(String username) {
		Token token = new Token(bean.createToken(username));
		return token;
	}
}
