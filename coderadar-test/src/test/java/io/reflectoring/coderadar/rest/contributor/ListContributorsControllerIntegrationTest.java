package io.reflectoring.coderadar.rest.contributor;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import io.reflectoring.coderadar.contributor.port.driver.GetContributorsForPathCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.GetContributorResponse;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

public class ListContributorsControllerIntegrationTest extends ControllerTestTemplate {

  private long projectId;

  @BeforeEach
  public void setUp() throws Exception {
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

    projectId = fromJson(result.getResponse().getContentAsString(), IdResponse.class).getId();
  }

  @Test
  public void listContributors() throws Exception {
    MvcResult result =
        mvc()
            .perform(
                get("/projects/" + projectId + "/contributors")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(documentListContributors())
            .andReturn();

    List<GetContributorResponse> contributors =
        fromJson(
            new TypeReference<List<GetContributorResponse>>() {},
            result.getResponse().getContentAsString());

    Assertions.assertThat(contributors.size()).isEqualTo(2);
  }

  @Test
  public void listContributorsForFile() throws Exception {
    GetContributorsForPathCommand command =
        new GetContributorsForPathCommand(
            "GetMetricsForCommitCommand.java", "e9f7ff6fdd8c0863fdb5b24c9ed35a3651e20382");

    MvcResult result =
        mvc()
            .perform(
                get("/projects/" + projectId + "/contributors/path")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(command)))
            .andExpect(status().isOk())
            .andDo(documentListContributorsForFile())
            .andReturn();

    List<GetContributorResponse> contributors =
        fromJson(
            new TypeReference<List<GetContributorResponse>>() {},
            result.getResponse().getContentAsString());
    GetContributorResponse contributor = contributors.get(0);

    Assertions.assertThat(contributors.size()).isEqualTo(1);
    Assertions.assertThat(contributor.getDisplayName()).isEqualTo("maximAtanasov");
    Assertions.assertThat(contributor.getNames())
        .containsExactlyInAnyOrder("maximAtanasov", "Atanasov");
    Assertions.assertThat(contributor.getEmailAddresses())
        .containsExactly("maksim.atanasov@adesso.de");
  }

  @Test
  public void listContributorsForModule() throws Exception {
    GetContributorsForPathCommand command =
        new GetContributorsForPathCommand(
            "testModule1", "e9f7ff6fdd8c0863fdb5b24c9ed35a3651e20382");

    MvcResult result =
        mvc()
            .perform(
                get("/projects/" + projectId + "/contributors/path")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(command)))
            .andExpect(status().isOk())
            .andReturn();

    List<GetContributorResponse> contributors =
        fromJson(
            new TypeReference<List<GetContributorResponse>>() {},
            result.getResponse().getContentAsString());
    GetContributorResponse first = contributors.get(0);
    GetContributorResponse second = contributors.get(1);

    Assertions.assertThat(contributors.size()).isEqualTo(2);
    Assertions.assertThat(first.getDisplayName()).isEqualTo("Krause");
    Assertions.assertThat(second.getDisplayName()).isEqualTo("maximAtanasov");
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

  private ResultHandler documentListContributorsForFile() {
    ConstrainedFields<GetContributorsForPathCommand> fields =
        fields(GetContributorsForPathCommand.class);

    return document(
        "contributors/list/path",
        requestFields(
            fields
                .withPath("path")
                .description("The path for. Either it is a filepath or a module path."),
            fields
                .withPath("commitHash")
                .description("Get the critical file only if it belongs to this commit tree.")));
  }
}
