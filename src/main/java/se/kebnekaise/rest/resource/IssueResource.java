package se.kebnekaise.rest.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.kebnekaise.java.spring.model.Issue;
import se.kebnekaise.java.spring.service.IssueService;
import se.kebnekaise.java.spring.service.WorkItemService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Component
@Produces("application/json")
@Consumes("application/json")
@Path("/issues")
public class IssueResource {

    @Autowired
    IssueService service;
    @Autowired
    WorkItemService workItemService;

    @POST
    public Response createIssue(Issue issue){
        return Response.ok(service.createOrUpdateIssue(new Issue(issue.getTitle(),issue.getDescription()))).build();
    }

    @PUT
    @Path("/{issue}")
    public Response updateIssue(@PathParam("issue")String title, Issue issue){
        Issue fromDatabase = service.findIssueByTitle(title);
        fromDatabase.setTitle(issue.getTitle());
        fromDatabase.setDescription(issue.getDescription());
        return Response.ok(service.createOrUpdateIssue(fromDatabase)).build();
    }

    @PUT
    @Path("/{issue}/{workitem}")
    public Response setWorkItem(@PathParam("issue")String title, @PathParam("workitem")String workitem){
        Issue issue = service.findIssueByTitle(title);
        issue.addWorkItem(workItemService.findByTitle(workitem));
        return Response.ok(service.createOrUpdateIssue(issue)).build();

    }
}
