package se.kebnekaise.rest.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.kebnekaise.java.spring.model.Team;
import se.kebnekaise.java.spring.model.User;
import se.kebnekaise.java.spring.service.TeamService;
import se.kebnekaise.java.spring.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Component
@Produces("application/json")
@Consumes("application/json")
@Path("/users")
public final class UserResource
{
	@Autowired
	private UserService service;

	@Autowired
	private TeamService teamService;

	@POST
	public Response createUser(@Context UriInfo uriInfo, User user) {
		User result = service.createOrUpdate(user);
		URI uri = uriInfo.getAbsolutePathBuilder().path(user.getUsername()).build();
		return Response.created(uri)
				.entity(result)
				.build();
	}

	@GET
	public Response findAllUsers() {
		Iterable result = service.findAllUsers();
		return Response.ok()
				.entity(result)
				.build();
	}

	@GET
	@Path("/{username}")
	public Response findUser(@PathParam("username") String username) {
		User user = service.findUserByUsername(username);
		System.out.println(user);
		return Response.ok()
				.entity(user)
				.build();
	}

	@DELETE
	@Path("/{username}")
	public Response deleteUser(@PathParam("username") String username) {
		/*
		* Design request:
		* service returns a User, so that we can return HTTP Code: 200 with the deleted user.
		* */
		User user = service.findUserByUsername(username);
		service.deleteUser(user);
		return Response.noContent()
				.build();
	}

	@PUT
	@Path("/{username}")
	public Response updateUser(@PathParam("username")String username, User  user){
		User fromDatabase = service.findUserByUsername(username);
		fromDatabase.setFirstname(user.getFirstname());
		fromDatabase.setSurname(user.getSurname());
		fromDatabase.setUsername(user.getUsername());

		return Response.ok(service.createOrUpdate(fromDatabase)).build();
	}

	@PUT
	@Path("/{firstname}/team")
	public Response setTeam(@PathParam("firstname")String name, Team teamName){
		Team team = teamService.getTeambyName(teamName.getTeamName());
		User user = service.findUserByFirstname(name);
		user.setTeam(team);
		return Response.ok(service.createOrUpdate(user)).build();
	}
}