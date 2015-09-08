package se.kebnekaise.rest.resource.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.kebnekaise.java.spring.model.WorkItem;
import se.kebnekaise.java.spring.service.WorkItemService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Produces("application/json")
@Consumes("application/json")
@Path("/items")
public final class ItemResource
{
	@Inject
	private WorkItemService service;

	@POST
	public Response createWorkItem(@Context UriInfo uriInfo, final WorkItem work){
		WorkItem result = service.createOrUpdateWorkItem(work);
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(result.getId())).build();
		return Response.created(uri)
				.entity(result)
				.build();
	}

	@GET
	@Path("/{item}")
	public Response getWorkItem(@PathParam("item") Long id) {
		WorkItem result = service.findById(id);
		return Response.ok()
				.entity(result)
				.build();
	}

	@DELETE
	@Path("/{item}")
	public Response deleteUser(@PathParam("item") Long id) {
		WorkItem result = service.findById(id);
		service.deleteWorkitem(id);
		return Response.noContent()
				.build();
	}


}