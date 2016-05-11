package org.wickedsource.coderadar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultMatcher;
import org.wickedsource.coderadar.core.domain.validation.ValidationErrorsDTO;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Coderadar.class})
public abstract class ControllerTestTemplate {

    private Logger logger = LoggerFactory.getLogger(ControllerTestTemplate.class);

    public <T> T fromJson(String json, Class<T> toClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(json, toClass);
    }

    public <T> String toJson(T object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public ResultMatcher validationErrorForField(String fieldName) {
        return result -> {
            String json = result.getResponse().getContentAsString();
            try {
                ValidationErrorsDTO errors = fromJson(json, ValidationErrorsDTO.class);
                Assert.assertTrue(String.format("expected at least one validation error for field %s", fieldName), errors.getErrorsForField(fieldName).size() > 0);
            } catch (Exception e) {
                logger.error(String.format("expected JSON representation of ValidationErrorsDTO but found '%s'", json), e);
                Assert.fail(String.format("expected JSON representation of ValidationErrorsDTO but found '%s'", json));
            }
        };
    }

}
