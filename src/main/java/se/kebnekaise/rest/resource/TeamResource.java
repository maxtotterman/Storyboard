package se.kebnekaise.rest.resource;

import se.kebnekaise.java.spring.model.Team;
<<<<<<< HEAD
import se.kebnekaise.java.spring.model.WorkItem;
=======
import se.kebnekaise.java.spring.model.User;
>>>>>>> 2332cf378cf6498025f40dc5a8ab458fbfdcd913
import se.kebnekaise.java.spring.service.TeamService;
import se.kebnekaise.java.spring.service.UserService;
import se.kebnekaise.java.spring.service.WorkItemService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
<<<<<<< HEAD
=======
import javax.ws.rs.core.UriInfo;
import java.net.URI;
>>>>>>> 2332cf378cf6498025f40dc5a8ab458fbfdcd913
import java.util.List;

@Produces("application/json")
@Consumes("application/json")
@Path("/teams")
<<<<<<< HEAD
public class TeamResource {

    @Inject
    private TeamService teamService;

    @Inject
    private UserService userService;

    @Inject
    private WorkItemService workItemService;

    @GET
    @Path("/all")
    public Response getAllTeams(){
        return Response.ok(teamService.getAllTeams()).build();
    }
    @PUT
    @Path("/{teamname}")
    public Response updateTeam(@PathParam("teamname")String teamName,Team team){
        Team teamFromDatabase = teamService.getTeambyName(teamName);
        teamFromDatabase.setTeamName(team.getTeamName());

        return Response.ok(teamService.createOrUpdate(teamFromDatabase)).build();
    }

    @DELETE
    @Path("/delete")
    public Response deleteTeam(Team team){
        teamService.deleteTeam(teamService.getTeambyName(team.getTeamName()));
        return Response.ok().build();
    }

    @POST
    public Response createTeam(Team team){
        return Response.ok(teamService.createOrUpdate(team)).build();
    }
    @GET
    @Path("/{teamName}")
    public Response getUsersFromTeam(@PathParam("teamName")String teamName){
            Team team = teamService.getTeambyName(teamName);
            return Response.ok(userService.findUsersByTeam(team)).build();
    }
    @GET
    @Path("/{teamName}/items")
    public Response getWorkItemsForTeam(@PathParam("teamName")String teamName){
        Team team = teamService.getTeambyName(teamName);
        List<WorkItem> list = workItemService.findWorkItemByTeam(team);
        list.forEach(System.out::println);
        return Response.ok().entity(list).build();
    }
=======
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
>>>>>>> 2332cf378cf6498025f40dc5a8ab458fbfdcd913
}
