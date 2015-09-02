package se.kebnekaise.rest.resources;

import se.kebnekaise.java.spring.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public final class UserResource
{

	@GET
	public String getAllUsers(){
		return "asd";
	}


	@GET
	@Path("{username}")
	public Response getUserByUserName(@PathParam("username") String username) {

		return Response.status(200)
				.entity("getUserByUserName is called, username : " + username).build();
	}

	@POST
	public User createUser(User user) {
		String result = "User saved : " + user;
		return user;
	}

}
