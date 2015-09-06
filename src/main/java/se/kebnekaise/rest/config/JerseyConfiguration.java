package se.kebnekaise.rest.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class JerseyConfiguration extends ResourceConfig
{
	public JerseyConfiguration() {
		packages("se.kebnekaise.rest");
	}
}