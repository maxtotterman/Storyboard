package se.kebnekaise.rest.resource;

import se.kebnekaise.java.spring.model.Team;
import se.kebnekaise.java.spring.model.WorkItem;
import se.kebnekaise.java.spring.service.TeamService;
import se.kebnekaise.java.spring.service.UserService;
import se.kebnekaise.java.spring.service.WorkItemService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Produces("application/json")
@Consumes("application/json")
@Path("/teams")
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
}
