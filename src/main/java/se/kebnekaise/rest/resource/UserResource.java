package se.kebnekaise.rest.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.kebnekaise.java.spring.model.Team;
import se.kebnekaise.java.spring.model.User;
import se.kebnekaise.java.spring.service.TeamService;
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

	@Autowired
	private TeamService teamService;

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

	@PUT
	@Path("/{firstname}/update")
	public Response updateUser(@PathParam("firstname")String name, User  user){
		User fromDatabase = service.findUserByFirstname(name);
		fromDatabase.setFirstname(user.getFirstname());
		fromDatabase.setSurname(user.getSurname());
		fromDatabase.setUsername(user.getUsername());

		return Response.ok(service.createOrUpdate(fromDatabase)).build();
	}
	@DELETE
	@Path("/{firstname}")
	public Response deleteUser(User user){
		service.deleteUser(user);
		return Response.accepted().build();
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
