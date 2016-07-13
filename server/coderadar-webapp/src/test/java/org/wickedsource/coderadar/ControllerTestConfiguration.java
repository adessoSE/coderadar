package org.wickedsource.coderadar;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.web.WebAppConfiguration;

@Configuration
@EnableSpringDataWebSupport
@WebAppConfiguration
public class ControllerTestConfiguration {

    @Bean
    public PagedResourcesAssembler pagedResourcesAssembler(){
        return new PagedResourcesAssembler(null, null);
    }
}
