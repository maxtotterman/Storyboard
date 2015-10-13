package se.kebnekaise.rest.resource;

import se.kebnekaise.java.spring.model.AuthAccessElement;
import se.kebnekaise.java.spring.model.AuthLoginElement;
import se.kebnekaise.java.spring.service.AuthServiceBean;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

@Produces("application/json")
@Consumes("application/json")
@Path("/login")
public class AuthenticationResource {

    @Inject
    AuthServiceBean authService;

    @POST
    @PermitAll
    public AuthAccessElement login(@Context HttpServletRequest request, AuthLoginElement loginElement,HttpServletResponse resp) {
        AuthAccessElement accessElement = authService.login(loginElement);
        if (accessElement != null) {
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_ID, accessElement.getAuthId());
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_TOKEN, accessElement.getAuthToken());
        }
        return accessElement;
    }


}
