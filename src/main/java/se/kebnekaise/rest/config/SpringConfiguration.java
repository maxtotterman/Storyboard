package se.kebnekaise.rest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = {
		"se.kebnekaise.java.spring",
		"se.kebnekaise.rest"
})
public class SpringConfiguration
{
}