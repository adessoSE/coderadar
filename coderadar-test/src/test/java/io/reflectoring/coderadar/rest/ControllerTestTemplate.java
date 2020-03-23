package io.reflectoring.coderadar.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(RestDocumentationExtension.class)
@Tag(ControllerTestTemplate.TAG)
public abstract class ControllerTestTemplate extends IntegrationTestTemplate {

  public static final String TAG = "ControllerTest";

  private MockMvc mvc;

  @Autowired private WebApplicationContext applicationContext;

  @BeforeEach
  public void setup(RestDocumentationContextProvider restDocumentation) {
    MockitoAnnotations.initMocks(this);
    mvc =
        MockMvcBuilders.webAppContextSetup(applicationContext)
            .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .build();
  }

  @AfterAll
  static void cleanUp() throws IOException {
    FileUtils.deleteDirectory(new File("coderadar-workdir/projects"));
  }

  @SuppressWarnings("unchecked")
  protected <T> ConstrainedFields<T> fields(Class<T> clazz) {
    return new ConstrainedFields(clazz);
  }

  public static class ConstrainedFields<T> {

    private final ConstraintDescriptions constraintDescriptions;
    private final Properties customValidationDescription = new Properties();

    ConstrainedFields(Class<T> input) {

      try {
        this.constraintDescriptions = new ConstraintDescriptions(input);
        this.customValidationDescription.load(
            getClass()
                .getClassLoader()
                .getResourceAsStream("CustomValidationDescription.properties"));
      } catch (IOException e) {
        throw new IllegalArgumentException(
            "unable to load properties for custom validation description");
      }
    }

    public FieldDescriptor withPath(String path) {
      return PayloadDocumentation.fieldWithPath(path)
          .attributes(
              Attributes.key("constraints")
                  .value(
                      StringUtils.collectionToDelimitedString(
                          this.constraintDescriptions.descriptionsForProperty(path), ". ")));
    }

    /** Returns field descriptor for custom validators. */
    public FieldDescriptor withCustomPath(String path) {
      return PayloadDocumentation.fieldWithPath(path)
          .attributes(
              Attributes.key("constraints")
                  .value(
                      StringUtils.collectionToDelimitedString(
                          Collections.singletonList(customValidationDescription.getProperty(path)),
                          ". ")));
    }
  }

  /**
   * Wraps the static document() method of RestDocs and configures it to pretty print request and
   * response JSON structures.
   */
  protected RestDocumentationResultHandler document(String identifier, Snippet... snippets) {
    return MockMvcRestDocumentation.document(
        identifier,
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        snippets);
  }

  protected MockMvc mvc() {
    return mvc;
  }

  public static <T> String toJson(T object) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
