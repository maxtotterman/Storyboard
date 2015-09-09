package se.kebnekaise.rest.resource.item;

import se.kebnekaise.java.spring.model.Issue;
import se.kebnekaise.java.spring.model.WorkItem;
import se.kebnekaise.java.spring.service.IssueService;
import se.kebnekaise.java.spring.service.WorkItemService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Produces("application/json")
@Consumes("application/json")
@Path("/items")
public final class ItemResource
{
	@Inject
	private WorkItemService service;

	@Inject
	private IssueService issueService;

//	@POST
//	public Response createWorkItem(@Context UriInfo uriInfo, @RequestBody WorkItem work) {
//		WorkItem result = service.createOrUpdateWorkItem(work);
//		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(result.getId())).build();
//		return Response.created(uri)
//				.entity(result)
//				.build();
//	}
	@POST
	public Response create(WorkItem work){
		return Response.ok(service.createOrUpdateWorkItem(work)).build();
	}

	@GET
	public Response searchItemByStatus(@Context UriInfo info) {
		String status = info.getQueryParameters().getFirst("status");
		String text = info.getQueryParameters().getFirst("text");

		if (status != null) {
			List<WorkItem> result = service.findByStatus(status);
			if (!result.isEmpty()) {
				return Response.ok()
						.entity(result)
						.build();
			}
		}
		if (text != null) {
			List<WorkItem> result = service.findWorkItemContaining(text);
			if (!result.isEmpty()) {
				return Response.ok()
						.entity(result)
						.build();
			}
		}
		throw new WebApplicationException(404);
	}

	@DELETE
	@Path("/{item}")
	public Response deleteItem(@PathParam("item") Long id) {
		WorkItem result = service.findById(id);
		service.deleteWorkitem(id);
		return Response.noContent()
				.build();
	}

	@PUT
	@Path("/{item}/status")
	public Response changeStatusForItem(@PathParam("item") Long id, WorkItem status) {
		WorkItem result = service.updateStatusForWorkItem(id, status.getStatus());
		return Response.ok()
				.entity(result)
				.build();
	}

	@GET
	@Path("/issues")
	public Response getItemWithIssue(){
		return Response.ok(issueService.findAllIssuesWithWorkItem()).build();
	}

	@POST
	@Path("/{item}/issues")
	public Response addIssueToItem(@PathParam("item") Long id, Issue issue){
		service.addIssueToWorkItem(id, issue);
		return Response.ok().build();
	}
}