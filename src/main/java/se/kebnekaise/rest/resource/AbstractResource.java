package se.kebnekaise.rest.resource;

import se.kebnekaise.java.spring.model.AuthAccessElement;
import se.kebnekaise.java.spring.service.AuthServiceBean;

import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractResource {

    @Inject
    AuthServiceBean authService;

    protected boolean checkAuth(String role, HttpHeaders httpHeaders){
        String userId = httpHeaders.getHeaderString(AuthAccessElement.PARAM_AUTH_ID);
        String authToken = httpHeaders.getHeaderString(AuthAccessElement.PARAM_AUTH_TOKEN);
        if(userId == null || userId.isEmpty() || authToken == null || authToken.isEmpty()) {
            System.out.println("EMPTY");
            return false;
        }
        Set<String> rolesAllowed = new LinkedHashSet<String>();
        rolesAllowed.add(role);
        if( ! authService.isAuthorized(userId,authToken, rolesAllowed))
        {
            return false;
        }
        return true;

    }
}
