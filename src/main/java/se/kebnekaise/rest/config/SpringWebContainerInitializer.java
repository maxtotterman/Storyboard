package se.kebnekaise.rest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Order(1)
public class SpringWebContainerInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		registerContextLoaderListener(servletContext);
		servletContext.setInitParameter("contextConfigLocation", "");
	}

	private void registerContextLoaderListener(ServletContext servletContext) {
		WebApplicationContext webContext;
		webContext = createWebAplicationContext(SpringConfiguration.class);
		servletContext.addListener(new ContextLoaderListener(webContext));
	}

	public WebApplicationContext createWebAplicationContext(Class... configClasses) {
		AnnotationConfigWebApplicationContext context;
		context = new AnnotationConfigWebApplicationContext();
		context.getEnvironment().setActiveProfiles("production");
		context.register(configClasses);
		return context;
	}
}