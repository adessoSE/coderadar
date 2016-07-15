package org.wickedsource.coderadar.testframework;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;

import static org.springframework.hateoas.MediaTypes.HAL_JSON;

@Configuration
@EnableSpringDataWebSupport
@WebAppConfiguration
public class ControllerTestConfiguration {

    @Bean
    public PagedResourcesAssembler pagedResourcesAssembler(){
        return new PagedResourcesAssembler(null, null);
    }

    @Bean
    public HttpMessageConverter messageConverter(){
        ObjectMapper halObjectMapper = new ObjectMapper();
        halObjectMapper.registerModule(new Jackson2HalModule());
        MappingJackson2HttpMessageConverter halConverter = new TypeConstrainedMappingJackson2HttpMessageConverter(
                ResourceSupport.class);
        halConverter.setSupportedMediaTypes(Arrays.asList(HAL_JSON));
        halConverter.setObjectMapper(halObjectMapper);
        return halConverter;
    }
}
