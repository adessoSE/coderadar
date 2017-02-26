package org.wickedsource.coderadar.testframework.template;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.hypermedia.JsonPathLinksSnippet;
import org.springframework.restdocs.hypermedia.JsonPathResponseFieldsSnippet;
import org.springframework.restdocs.hypermedia.LinkDescriptor;
import org.springframework.restdocs.hypermedia.LinksSnippet;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

public abstract class ControllerTestTemplate extends IntegrationTestTemplate {

  private MockMvc mvc;

  @Autowired private WebApplicationContext applicationContext;

  @Autowired private SequenceResetter sequenceResetter;

  @Rule
  public JUnitRestDocumentation restDocumentation =
      new JUnitRestDocumentation("build/generated-snippets");

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    mvc =
        MockMvcBuilders.webAppContextSetup(applicationContext)
            .apply(MockMvcRestDocumentation.documentationConfiguration(this.restDocumentation))
            .build();
  }

  @After
  public void reset() {
    sequenceResetter.resetAutoIncrementColumns(
        "git_log_entry",
        "file",
        "file_identity",
        "file_pattern",
        "job",
        "quality_profile",
        "quality_profile_metric",
        "module");

    sequenceResetter.resetSequences(
        "seq_proj_id", "seq_comm_id", "seq_acon_id", "seq_acof_id", "seq_ajob_id");
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
            getClass().getResourceAsStream("CustomValidationDescription.properties"));
      } catch (IOException e) {
        throw new IllegalArgumentException(
            "unable to load properties for custom validation description");
      }
    }

    public FieldDescriptor withPath(String path) {
      return fieldWithPath(path)
          .attributes(
              key("constraints")
                  .value(
                      StringUtils.collectionToDelimitedString(
                          this.constraintDescriptions.descriptionsForProperty(path), ". ")));
    }

    /** Returns field descriptor for custom validators. */
    public FieldDescriptor withCustomPath(String path) {
      return fieldWithPath(path)
          .attributes(
              key("constraints")
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
        identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()), snippets);
  }

  protected MockMvc mvc() {
    return mvc;
  }

  protected JsonPathResponseFieldsSnippet responseFieldsInPath(
      String jsonPath, FieldDescriptor... fieldDescriptors) {
    return new JsonPathResponseFieldsSnippet(jsonPath, fieldDescriptors);
  }

  protected LinksSnippet linksInPath(String jsonPath, LinkDescriptor... linkDescriptors) {
    return new JsonPathLinksSnippet(jsonPath, linkDescriptors);
  }
}
