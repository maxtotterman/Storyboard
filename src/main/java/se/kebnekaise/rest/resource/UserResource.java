package se.kebnekaise.rest.resource;

import se.kebnekaise.java.spring.model.Team;
import se.kebnekaise.java.spring.model.User;
import se.kebnekaise.java.spring.service.TeamService;
import se.kebnekaise.java.spring.service.UserService;
import se.kebnekaise.java.spring.service.WorkItemService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Produces("application/json")
@Consumes("application/json")
@Path("/users")
public final class UserResource
{
	@Inject
	private UserService service;

	@Inject
	private TeamService teamService;

	@Inject
	WorkItemService workItemService;

	@POST
	public Response createUser(@Context UriInfo uriInfo, User user) {
		User result = service.createNewUser(user);
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
	public Response updateUser(@PathParam("username") String username, User user) {
		User result = service.updateUser(username, user);
		return Response.ok()
				.entity(result)
				.build();
	}

	@PUT
	@Path("/{username}/team")
	public Response setTeam(@PathParam("username") String name, Team teamName) {
		Team team = teamService.getTeambyName(teamName.getTeamName());
		User user = service.findUserByUsername(name);
		System.out.println("UserResource.setTeam: " + user.getUsername());
		user.setTeam(team);
		team.addUser(user);
		teamService.updateTeam(team.getTeamName(),team);
		return Response.ok(service.updateUser(name, user)).build();
	}
	@GET
	@Path("{username}/items")
	public Response getAllWorkItems(@PathParam("username")String firstname){
		User user = service.findUserByUsername(firstname);
		return Response.ok(workItemService.findByUser(user)).build();
	}
}