package se.kebnekaise.rest.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.kebnekaise.java.spring.model.Team;
import se.kebnekaise.java.spring.model.User;
import se.kebnekaise.java.spring.service.TeamService;
import se.kebnekaise.java.spring.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Produces("application/json")
@Consumes("application/json")
@Path("/teams")
public class TeamResource
{

	@Inject
	private TeamService teamService;
	
	@POST
	public Response createTeam(@Context UriInfo uriInfo, Team team) {
		Team result = teamService.createTeam(team);
		URI uri = uriInfo.getAbsolutePathBuilder().path(team.getTeamName()).build();
		return Response.created(uri)
				.entity(result)
				.build();
	}

	@GET
	public Response getAllTeams() {
		Iterable result = teamService.getAllTeams();
		return Response.ok()
				.entity(result)
				.build();
	}

	@GET
	@Path("/{teamName}")
	public Response getUsersFromTeam(@PathParam("teamName") String teamName) {
		List<User> result = teamService.getAllUsersInTeam(teamName);
		return Response.ok()
				.entity(result)
				.build();
	}

	@PUT
	@Path("/{teamName}")
	public Response updateTeam(@PathParam("teamName") String teamName, Team team) {
		teamService.updateTeam(teamName, team);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{teamname}")
	public Response deleteTeam(@PathParam("teamname") String teamName) {
		Team result = teamService.getTeambyName(teamName);
		teamService.deleteTeam(result);
		return Response.noContent()
				.build();
	}
}
