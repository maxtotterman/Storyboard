package se.kebnekaise.rest.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.kebnekaise.java.spring.model.WorkItem;
import se.kebnekaise.java.spring.service.WorkItemService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Component
@Produces("application/json")
@Consumes("application/json")
@Path("/items")
public final class ItemResource
{
	@Autowired
	private  WorkItemService service;

	@POST
	public Response createWorkItem(final WorkItem work){
		return Response.ok(service.createOrUpdateWorkItem(work)).build();
	}

	@PUT
	@Path("/{workitem}")
	public Response updateWorkItem(@PathParam("workitem") WorkItem work){
		return Response.ok(service.createOrUpdateWorkItem(work)).build();
	}

	@DELETE
	@Path("/{workitem}")
	public Response deleteWorkItem(@PathParam("workitem") Long id){
		service.deleteWorkitem(id);
		return Response.ok().build();
	}
}
