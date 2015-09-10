package se.kebnekaise.rest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public final class JacksonConfiguration implements ContextResolver<ObjectMapper>{

	final ObjectMapper defaultObjectMapper;

	public JacksonConfiguration() {
		defaultObjectMapper = createDefaultMapper();
	}

	@Override public ObjectMapper getContext(Class<?> type) {
		return defaultObjectMapper;
	}
	private static ObjectMapper createDefaultMapper() {
		final ObjectMapper result = new ObjectMapper();
		result.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		return result;
	}
}
