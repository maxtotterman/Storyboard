package se.kebnekaise.rest.resource;

import se.kebnekaise.java.spring.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Produces("application/json")
@Consumes("application/json")
@Path("/search")
public class SearchResource
{
	@Inject
	private UserService service;

	@GET
	public Response getUsers(@Context UriInfo info) {

		String firstname = info.getQueryParameters().getFirst("firstname");
		String surname = info.getQueryParameters().getFirst("surname");
		String username = info.getQueryParameters().getFirst("username");
		String id = info.getQueryParameters().getFirst("id");

		if (firstname != null) {
			return Response.ok(service.findUserByFirstname(firstname)).build();

		}
		if (surname != null) {
			return Response.ok(service.findUserBySurname(surname)).build();

		}
		if (username != null) {
			return Response.ok(service.findUserByUsername(username)).build();
		}
		if (id != null) {
			return Response.ok(service.findUserByIdentity(id)).build();
		}
		else {
			return Response.noContent().build();
		}
	}
}
