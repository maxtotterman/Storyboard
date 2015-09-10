package se.kebnekaise.rest.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.kebnekaise.java.spring.model.Issue;
import se.kebnekaise.java.spring.model.Team;
import se.kebnekaise.java.spring.service.IssueService;
import se.kebnekaise.java.spring.service.WorkItemService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Produces("application/json")
@Consumes("application/json")
@Path("/issues")
public class IssueResource
{

	@Inject
	IssueService service;

	@Inject
	WorkItemService workItemService;

	@POST
	public Response createIssue(@Context UriInfo uriInfo, Issue issue) {
		Issue result = service.createNewIssue(issue);
		if (result != null) {
			URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(result.getId())).build();
			return Response.created(uri)
					.entity(result)
					.build();
		}
		throw new BadRequestException("Could not create an issue");
	}

	@PUT
	@Path("/{issue}")
	public Response updateIssue(@PathParam("issue") Long id, Issue issue) {
		Issue result = service.updateIssue(id, issue);
		return Response.ok()
				.entity(result)
				.build();
	}
}
