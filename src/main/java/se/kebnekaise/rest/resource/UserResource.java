package se.kebnekaise.rest.resource;

import se.kebnekaise.java.spring.model.User;
import se.kebnekaise.java.spring.service.UserService;
import se.kebnekaise.java.spring.service.WorkItemService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Produces("application/json")
@Consumes("application/json")
@Path("/users")
public final class UserResource
{
	@Inject
	private UserService service;

	@Inject
	WorkItemService workItemService;

	@POST
	public Response createUser(@Context UriInfo uriInfo, User user) {
		User result = service.createNewUser(user);
		if (result != null) {
			URI uri = uriInfo.getAbsolutePathBuilder().path(result.getUsername()).build();
			return Response.created(uri)
					.entity(result)
					.build();
		}
		throw new BadRequestException();
	}

	@GET
	@Path("/{username}")
	public Response findUser(@PathParam("username") String username) {
		User user = service.findUserByUsername(username);
		if (user != null) {
			return Response.ok()
					.entity(user)
					.build();
		}
		throw new NotFoundException("Could not find user");
	}

	@DELETE
	@Path("/{username}")
	public Response deleteUser(@PathParam("username") String username) {
		User user = service.findUserByUsername(username);
		if (user != null) {
			service.deleteUser(user);
			return Response.noContent()
					.build();
		}
		throw new NotFoundException("Could not find user");
	}

	@PUT
	@Path("/{username}")
	public Response updateUser(@PathParam("username") String username, User user) {
		User result = service.updateUser(username, user);
		if (result != null) {
			return Response.ok()
					.entity(result)
					.build();
		}
		throw new BadRequestException("JSON malformed");
	}

	@GET
	@Path("{username}/items")
	public Response getAllWorkItemsForUser(@PathParam("username") String username) {
		User user = service.findUserByUsername(username);
		if (user != null) {
			return Response.ok()
					.entity(user.getWorkitems())
					.build();
		}
		throw new NotFoundException("Could not find user");
	}

	@POST
	@Path("/{username}/items/{workItem}")
	public Response setWorkItem(@PathParam("username") String username, @PathParam("workItem") Long id) {
		User result = service.addWorkItemToUser(username, id);
		if (result != null) {
			return Response.ok()
					.entity(result.getWorkitems())
					.build();
		}
		throw new NotFoundException("Could find user with id, or could not find item with id)");
	}

}