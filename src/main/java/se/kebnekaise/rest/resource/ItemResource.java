package se.kebnekaise.rest.resource;

import se.kebnekaise.java.spring.model.Issue;
import se.kebnekaise.java.spring.model.WorkItem;
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
@Path("/items")
public final class ItemResource
{
	@Inject
	private WorkItemService service;

	@POST
	public Response createWorkItem(@Context UriInfo uriInfo, WorkItem work) {
		WorkItem result = service.createOrUpdateWorkItem(work);
		if (result != null) {
			URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(result.getId())).build();
			return Response.created(uri)
					.entity(result)
					.build();
		}
		throw new BadRequestException("Could not create item, malformed JSON");
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
		}else{
			return Response.ok(service.findAll()).build();
		}
		throw new NotFoundException();
	}

	@DELETE
	@Path("/{item}")
	public Response deleteItem(@PathParam("item") Long id) {
		WorkItem item = service.deleteWorkitem(id);
		if (item != null) {
			return Response.noContent()
					.build();
		}
		throw new NotFoundException("Item was not found");
	}

	@PUT
	@Path("/{item}/status")
	public Response changeStatusForItem(@PathParam("item") Long id, WorkItem status) {
		WorkItem result = service.updateStatusForWorkItem(id, status.getStatus());
		if (result != null) {
			return Response.ok()
					.entity(result)
					.build();
		}
		throw new BadRequestException("Could not change status");
	}

	@GET
	@Path("/issues")
	public Response getItemWithIssue() {
		return Response.ok(service.getAllWorkitemsWithAnIssue()).build();
	}

	@POST
	@Path("/{item}/issues")
	public Response addIssueToItem(@PathParam("item") Long id, Issue issue) {
		WorkItem result = service.addIssueToWorkItem(id, issue);

		if (result != null) {
			return Response.ok()
					.entity(result)
					.build();
		}
		throw new NotFoundException();
	}
	@POST
	@Path("/{teamName}/items")
	public Response addItemToTeam(@PathParam("teamName")String teamName, WorkItem workItem){
		WorkItem result = service.addWorkitemToTeam(teamName, workItem);
		if(result != null){
			return Response.ok().entity(result).build();
		}
		throw new NotFoundException();
	}


}