package se.kebnekaise.rest.config.filter;


import se.kebnekaise.java.spring.service.AuthBean;
import se.kebnekaise.rest.annotation.Secured;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter
{
	@Inject
	AuthBean bean;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		// Get the HTTP Authorization header from the request
		String authorizationHeader =
				requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		// Check if the HTTP Authorization header is present and formatted correctly
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new NotAuthorizedException("Authorization header must be provided");
		}

		// Extract the token from the HTTP Authorization header
		String token = authorizationHeader.substring("Bearer".length()).trim();

		try {
			// Validate the token
			validateToken(token);
		}
		catch (Exception e) {
			requestContext.abortWith(
					Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

	private void validateToken(String token) throws Exception {
		// Check if it was issued by the server and if it's not expired
		bean.checkToken(token);
	}
}