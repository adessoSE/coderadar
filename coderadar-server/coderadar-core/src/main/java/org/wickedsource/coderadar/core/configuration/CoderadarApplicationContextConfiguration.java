package org.wickedsource.coderadar.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/** Configuration class for adding / overriding beans in the Spring application context. */
@Configuration
public class CoderadarApplicationContextConfiguration {

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		// we want to resolve configuration parameters to NULL so we can give the user a meaningful log output when
		// some parameter is invalid when starting the application.
		configurer.setNullValue("");
		return configurer;
	}
}
