package se.kebnekaise.rest.service;

import se.kebnekaise.rest.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/users")
public final class UserService
{
	@GET
	@Path("{username}")
	@Produces("application/json")
	public Response getUserByUserName(@PathParam("username") String username) {

		return Response.status(200)
				.entity("getUserByUserName is called, username : " + username).build();

	}

	@POST
	@Consumes("application/json")
	public Response createUser(User user){
		String result = "User saved : " + user;
		return Response.status(201).entity(result).build();
	}

}
