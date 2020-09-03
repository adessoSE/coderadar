package io.reflectoring.coderadar.rest.query;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.query.domain.ContributorsForFile;
import io.reflectoring.coderadar.query.domain.FileAndCommitsForTimePeriod;
import io.reflectoring.coderadar.query.port.driver.criticalfiles.GetFilesWithContributorsCommand;
import io.reflectoring.coderadar.query.port.driver.criticalfiles.GetFrequentlyChangedFilesCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

class GetCriticalFilesControllerTest extends ControllerTestTemplate {
  private long projectId;

  @BeforeEach
  void setUp() throws Exception {
    URL testRepoURL = this.getClass().getClassLoader().getResource("test-repository");
    CreateProjectCommand command1 =
        new CreateProjectCommand(
            "test-project",
            "username",
            "password",
            Objects.requireNonNull(testRepoURL).toString(),
            false,
            null);
    MvcResult result =
        mvc()
            .perform(
                post("/api/projects")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(command1)))
            .andReturn();

    projectId = fromJson(result.getResponse().getContentAsString(), IdResponse.class).getId();
  }

  @Test
  void throwsExceptionWhenNoFilePatternsAreDefined() throws Exception {
    GetFilesWithContributorsCommand command =
        new GetFilesWithContributorsCommand("e9f7ff6fdd8c0863fdb5b24c9ed35a3651e20382", 1);
    MvcResult result =
        mvc()
            .perform(
                get("/api/projects/" + projectId + "/files/critical")
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
  void getCriticalFilesSuccessfully() throws Exception {
    CreateFilePatternCommand command =
        new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    mvc()
        .perform(
            post("/api/projects/" + projectId + "/filePatterns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(command)))
        .andExpect(status().isCreated())
        .andReturn();

    GetFilesWithContributorsCommand command1 =
        new GetFilesWithContributorsCommand("e9f7ff6fdd8c0863fdb5b24c9ed35a3651e20382", 1);

    MvcResult result =
        mvc()
            .perform(
                get("/api/projects/" + projectId + "/files/critical")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(command1)))
            .andExpect(status().isOk())
            .andReturn();

    List<ContributorsForFile> response =
        fromJson(
            new TypeReference<>() {},
            result.getResponse().getContentAsString());

    Assertions.assertThat(response.size()).isEqualTo(1);
    Assertions.assertThat(response.get(0).getContributors()).containsExactly("maximAtanasov");
    Assertions.assertThat(response.get(0).getPath()).isEqualTo("GetMetricsForCommitCommand.java");
  }

  @Test
  void getCriticalFilesWithTwoContributors() throws Exception {
    CreateFilePatternCommand command =
        new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    mvc()
        .perform(
            post("/api/projects/" + projectId + "/filePatterns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(command)))
        .andExpect(status().isCreated())
        .andReturn();

    GetFilesWithContributorsCommand command1 =
        new GetFilesWithContributorsCommand("e9f7ff6fdd8c0863fdb5b24c9ed35a3651e20382", 2);

    MvcResult result =
        mvc()
            .perform(
                get("/api/projects/" + projectId + "/files/critical?numOfContr=2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(command1)))
            .andExpect(status().isOk())
            .andDo(documentGetFilesWithContributors())
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

  @Test
  void getFrequentlyChangedFiles() throws Exception {
    CreateFilePatternCommand command =
        new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    mvc()
        .perform(
            post("/api/projects/" + projectId + "/filePatterns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(command)))
        .andExpect(status().isCreated())
        .andReturn();

    GetFrequentlyChangedFilesCommand command1 =
        new GetFrequentlyChangedFilesCommand(
            "e9f7ff6fdd8c0863fdb5b24c9ed35a3651e20382", new Date(1582588800000L) /*2020-02-25*/, 1);

    MvcResult mvcResult =
        mvc()
            .perform(
                get("/api/projects/" + projectId + "/files/modification/frequency")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(command1)))
            .andExpect(status().isOk())
            .andDo(documentGetFrequentlyChangedFiles())
            .andReturn();

    List<FileAndCommitsForTimePeriod> result =
        fromJson(
            new TypeReference<List<FileAndCommitsForTimePeriod>>() {},
            mvcResult.getResponse().getContentAsString());

    FileAndCommitsForTimePeriod resultItem = result.get(0);

    Assertions.assertThat(result.size()).isEqualTo(1);
    Assertions.assertThat(resultItem.getPath()).isEqualTo("testModule1/NewRandomFile.java");
    Assertions.assertThat(resultItem.getCommits().size()).isEqualTo(1);
    Assertions.assertThat(resultItem.getCommits().get(0).getComment())
        .isEqualTo("modify testModule1/NewRandomFile.java");
  }

  private ResultHandler documentGetFilesWithContributors() {
    ConstrainedFields<GetFilesWithContributorsCommand> fields =
        fields(GetFilesWithContributorsCommand.class);

    return document(
        "metrics/criticalFiles/numberOfContributors",
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

  private ResultHandler documentGetFrequentlyChangedFiles() {
    ConstrainedFields<GetFrequentlyChangedFilesCommand> fields =
        fields(GetFrequentlyChangedFilesCommand.class);

    return document(
        "metrics/criticalFiles/modificationFrequency",
        requestFields(
            fields
                .withPath("commitHash")
                .description(
                    "Only search for critical files in the file tree of the given commit."),
            fields.withPath("startDate").description("Start date of the time period."),
            fields
                .withPath("frequency")
                .description(
                    "How often a file needs to be changed in the time period to be declared as critical.")));
  }
}
