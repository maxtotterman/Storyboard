package se.kebnekaise.rest.resource;

import se.kebnekaise.java.spring.model.Team;
import se.kebnekaise.java.spring.model.User;
import se.kebnekaise.java.spring.model.WorkItem;
import se.kebnekaise.java.spring.service.TeamService;
import se.kebnekaise.java.spring.service.WorkItemService;

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

    @Inject
    private WorkItemService workItemService;

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
    @GET
    @Path("/{teamName}/items")
    public Response getWorkItemsForTeam(@PathParam("teamName")String teamName){
        Team team = teamService.getTeambyName(teamName);
        List<WorkItem> list = workItemService.findWorkItemByTeam(team);
        return Response.ok(workItemService.findWorkItemByTeam(team)).build();
    }

	@POST
	@Path("/{teamName}/users")
	public Response addUserToTeam(@PathParam("teamName") String teamname, User user) {
		User newUser = teamService.addUserToTeam(teamname, user);
		return Response.ok()
				.entity(newUser)
				.build();
	}
	
	@GET
	@Path("/{teamName}/users")
	public Response getUsersFromTeam(@PathParam("teamName") String teamName) {
		List<User> result = teamService.getAllUsersInTeam(teamName);
		return Response.ok()
				.entity(result)
				.build();
	}

}
