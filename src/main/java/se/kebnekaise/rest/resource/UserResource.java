package se.kebnekaise.rest.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.kebnekaise.java.spring.model.User;
import se.kebnekaise.java.spring.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Component
@Produces("application/json")
@Consumes("application/json")
@Path("/users")
public final class UserResource
{
	@Autowired
	private UserService service;

	@POST
	public Response createUser(User user){
		return Response.ok(service.createOrUpdate(user)).build();
	}
	@GET
	@Path("/{firstname}")
	public Response findUser(@PathParam("firstname") String firstName){
			return Response.ok(service.findUserByFirstname(firstName)).build();
	}

	@GET
	@Path("/all")
	public Response findAllUsers(){
		return Response.ok(service.findAllUsers()).build();
	}

	@POST
	@Path("/{firstname}/update")
	public Response updateUser(User user){
		return Response.ok(service.createOrUpdate(user)).build();
	}
	@DELETE
	@Path("/{firstname}")
	public Response deleteUser(User user){
		service.deleteUser(user);
		return Response.accepted().build();
	}



}
