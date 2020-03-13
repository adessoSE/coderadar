package io.reflectoring.coderadar.rest.contributor;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

public class ListContributorsControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  public void listContributors() throws Exception {
    URL testRepoURL = this.getClass().getClassLoader().getResource("test-repository");
    CreateProjectCommand command1 =
        new CreateProjectCommand(
            "test-project",
            "username",
            "password",
            Objects.requireNonNull(testRepoURL).toString(),
            false,
            null,
            null);
    MvcResult result =
        mvc()
            .perform(
                post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command1)))
            .andReturn();

    long projectId = fromJson(result.getResponse().getContentAsString(), IdResponse.class).getId();

    result =
        mvc()
            .perform(
                get("/projects/" + projectId + "/contributors")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(documentListContributors())
            .andReturn();

    List<Contributor> contributors =
        fromJson(
            new TypeReference<List<Contributor>>() {}, result.getResponse().getContentAsString());

    Assertions.assertThat(contributors.size()).isEqualTo(2);
  }

  private ResultHandler documentListContributors() {
    return document(
        "contributors/list",
        responseFields(
            fieldWithPath("[]").description("Array of all contributors in this project"),
            fieldWithPath("[].id").description("The id of the contributor"),
            fieldWithPath("[].displayName").description("Display name of the contributor"),
            fieldWithPath("[].names")
                .description("Set of names that the contributor has used in git commits"),
            fieldWithPath("[].emailAddresses")
                .description("Set of email addresses of the contributor")));
  }
}
