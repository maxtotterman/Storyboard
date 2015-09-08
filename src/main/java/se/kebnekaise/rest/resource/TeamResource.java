package se.kebnekaise.rest.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.kebnekaise.java.spring.model.Team;
import se.kebnekaise.java.spring.service.TeamService;
import se.kebnekaise.java.spring.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Component
@Produces("application/json")
@Consumes("application/json")
@Path("/teams")
public class TeamResource {

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @GET
    @Path("/all")
    public Response getAllTeams(){
        return Response.ok(teamService.getAllTeams()).build();
    }
    @PUT
    @Path("/{teamname}")
    public Response updateTeam(Team team){
        return Response.ok(teamService.createOrUpdate(team)).build();
    }

    @DELETE
    @Path("/delete")
    public Response deleteTeam(Team team){
        teamService.deleteTeam(team);
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
}
