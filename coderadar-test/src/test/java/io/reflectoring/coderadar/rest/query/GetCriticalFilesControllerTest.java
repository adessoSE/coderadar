package io.reflectoring.coderadar.rest.query;

import com.fasterxml.jackson.core.type.TypeReference;
import io.reflectoring.coderadar.contributor.port.driver.GetCriticalFilesCommand;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.query.domain.ContributorsForFile;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import java.net.URL;
import java.util.List;
import java.util.Objects;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetCriticalFilesControllerTest extends ControllerTestTemplate {
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
  public void throwsExceptionWhenNoFilePatternsAreDefined() throws Exception {
    GetCriticalFilesCommand command =
        new GetCriticalFilesCommand("e9f7ff6fdd8c0863fdb5b24c9ed35a3651e20382", 1);
    MvcResult result =
        mvc()
            .perform(
                get("/projects/" + projectId + "/files/critical")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(command)))
            .andExpect(status().isUnprocessableEntity())
            .andReturn();

    ErrorMessageResponse response =
        fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class);

    Assertions.assertThat(response.getErrorMessage())
        .isEqualTo("No file patterns defined for this project");
  }

  @Test
  public void getCriticalFilesSuccessfully() throws Exception {
    CreateFilePatternCommand command =
        new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    mvc()
        .perform(
            post("/projects/" + projectId + "/filePatterns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(command)))
        .andExpect(status().isCreated())
        .andReturn();

    GetCriticalFilesCommand command1 =
        new GetCriticalFilesCommand("e9f7ff6fdd8c0863fdb5b24c9ed35a3651e20382", 1);

    MvcResult result =
        mvc()
            .perform(
                get("/projects/" + projectId + "/files/critical")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(command1)))
            .andExpect(status().isOk())
            .andDo(documentGetCriticalFiles())
            .andReturn();

    List<ContributorsForFile> response =
        fromJson(
            new TypeReference<List<ContributorsForFile>>() {},
            result.getResponse().getContentAsString());

    Assertions.assertThat(response.size()).isEqualTo(1);
    Assertions.assertThat(response.get(0).getContributors()).containsExactly("maximAtanasov");
    Assertions.assertThat(response.get(0).getPath()).isEqualTo("GetMetricsForCommitCommand.java");
  }

  @Test
  public void getCriticalFilesWithTwoContributors() throws Exception {
    CreateFilePatternCommand command =
        new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    mvc()
        .perform(
            post("/projects/" + projectId + "/filePatterns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(command)))
        .andExpect(status().isCreated())
        .andReturn();

    GetCriticalFilesCommand command1 =
        new GetCriticalFilesCommand("e9f7ff6fdd8c0863fdb5b24c9ed35a3651e20382", 2);

    MvcResult result =
        mvc()
            .perform(
                get("/projects/" + projectId + "/files/critical?numOfContr=2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(command1)))
            .andExpect(status().isOk())
            .andReturn();

    List<ContributorsForFile> response =
        fromJson(
            new TypeReference<List<ContributorsForFile>>() {},
            result.getResponse().getContentAsString());
    ContributorsForFile responseItem = response.get(0);

    Assertions.assertThat(response.size()).isEqualTo(1);
    Assertions.assertThat(responseItem.getPath()).isEqualTo("testModule1/NewRandomFile.java");
    Assertions.assertThat(responseItem.getContributors())
        .containsExactlyInAnyOrder("maximAtanasov", "Krause");
  }

  private ResultHandler documentGetCriticalFiles() {
    ConstrainedFields<GetCriticalFilesCommand> fields = fields(GetCriticalFilesCommand.class);

    return document(
        "metrics/criticalFiles",
        requestFields(
            fields
                .withPath("commitHash")
                .description(
                    "Only search for critical files in the file tree of the given commit."),
            fields
                .withPath("numberOfContributors")
                .description(
                    "The amount of contributors for which a file is considered critical.")));
  }
}
