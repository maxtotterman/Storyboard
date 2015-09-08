package se.kebnekaise.rest.resource.item;

import org.springframework.beans.factory.annotation.Autowired;
import se.kebnekaise.java.spring.model.WorkItem;
import se.kebnekaise.java.spring.service.WorkItemService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@Produces("application/json")
@Consumes("application/json")
@Path("/items/search")
public final class ItemSearchResource
{
	@Inject
	private WorkItemService service;

	@GET
	public Response searchItem(@QueryParam("status") String status){
		List<WorkItem> items = service.findByStatus(status);
		return Response.ok()
				.entity(items)
				.build();
	}


}
