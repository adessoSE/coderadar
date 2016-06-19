package org.wickedsource.coderadar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.hypermedia.Link;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.wickedsource.coderadar.core.rest.validation.ErrorDTO;
import org.wickedsource.coderadar.core.rest.validation.ValidationExceptionHandler;

import java.io.IOException;
import java.util.List;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

public abstract class ControllerTestTemplate {

    private Logger logger = LoggerFactory.getLogger(ControllerTestTemplate.class);

    private MockMvc mvc;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

    public <T> T fromJson(String json, Class<T> toClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(json, toClass);
    }

    /**
     * Maps the given object to JSON.
     */
    public <T> String toJson(T object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    /**
     * Maps the given object to JSON, ignoring the "links" property that contains the
     * hypermedia links. This method should be used for mapping request payload only,
     * since the hypermedia links are expected in response payloads.
     */
    public <T> String toJsonWithoutLinks(T object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(object.getClass(), IgnoreLinksMixin.class);
        return mapper.writeValueAsString(object);
    }

    public ResultMatcher validationErrorForField(String fieldName) {
        return result -> {
            String json = result.getResponse().getContentAsString();
            try {
                ErrorDTO errors = fromJson(json, ErrorDTO.class);
                Assert.assertTrue(String.format("expected at least one validation error for field %s", fieldName), errors.getErrorsForField(fieldName).size() > 0);
            } catch (Exception e) {
                logger.error(String.format("expected JSON representation of ValidationErrorsDTO but found '%s'", json), e);
                Assert.fail(String.format("expected JSON representation of ValidationErrorsDTO but found '%s'", json));
            }
        };
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(getController())
                .setControllerAdvice(new ValidationExceptionHandler())
                .apply(MockMvcRestDocumentation.documentationConfiguration(this.restDocumentation))
                .build();
    }

    protected <T> ConstrainedFields<T> fields(Class<T> clazz) {
        return new ConstrainedFields(clazz);
    }

    public static class ConstrainedFields<T> {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<T> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        public FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }

    abstract class IgnoreLinksMixin {
        @JsonIgnore
        abstract List<Link> getLinks();
    }

    /**
     * Wraps the static document() method of RestDocs and configures it to pretty print request and
     * response JSON structures.
     */
    protected RestDocumentationResultHandler document(String identifier, Snippet... snippets) {
        return MockMvcRestDocumentation.document(identifier,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                snippets);
    }

    protected MockMvc mvc() {
        return mvc;
    }

    protected abstract Object getController();

    protected <T> ResultMatcher contains(Class<T> clazz) {
        return result -> {
            String json = result.getResponse().getContentAsString();
            try {
                T object = fromJson(json, clazz);
                Assert.assertNotNull(object);
            } catch (Exception e) {
                Assert.fail(String.format("expected JSON representation of class %s but found '%s'", clazz,  json));
            }
        };
    }

}
