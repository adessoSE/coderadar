package io.reflectoring.coderadar.rest.query;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.type.TypeReference;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.query.domain.CommitLog;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import java.net.URL;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class GetCommitLogControllerTest extends ControllerTestTemplate {

  private Long projectId;

  @BeforeEach
  void setUp() throws Exception {
    URL testRepoURL = this.getClass().getClassLoader().getResource("test-repository");
    CreateProjectCommand command1 =
        new CreateProjectCommand(
            "test-project", "username", "password", testRepoURL.toString(), false, null, null);
    MvcResult result =
        mvc()
            .perform(
                post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command1)))
            .andReturn();

    projectId = fromJson(result.getResponse().getContentAsString(), IdResponse.class).getId();
  }

  @Test
  void returnsAllCommitsInProject() throws Exception {
    MvcResult result =
        mvc()
            .perform(
                get("/projects/" + projectId + "/commitLog")
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(
                document(
                    "commit/log",
                    responseFields(
                        fieldWithPath("[]")
                            .description(
                                "Array of the commits in this project in a git log similar format."),
                        fieldWithPath("[].refs")
                            .description("Array of strings with the refs pointing to the commit"),
                        fieldWithPath("[].parents")
                            .description("Array of strings with the parents of the commit"),
                        fieldWithPath("[].hash").description("The hash of the commit."),
                        fieldWithPath("[].author.name").description("The name of the author"),
                        fieldWithPath("[].author.email").description("The email of the author"),
                        fieldWithPath("[].author.timestamp")
                            .description("The timestamp of the commit"),
                        fieldWithPath("[].author")
                            .description("Object containing author information about the commit."),
                        fieldWithPath("[].subject")
                            .description(
                                "The comment of this commit truncated to the first 100 characters."),
                        fieldWithPath("[].analyzed")
                            .description("Whether this commit is already analyzed or not."))))
            .andReturn();

    List<CommitLog> commits =
        fromJson(
            new TypeReference<List<CommitLog>>() {}, result.getResponse().getContentAsString());

    Assertions.assertEquals(15, commits.size());
    Assertions.assertEquals("add Finding.java", commits.get(commits.size() - 1).getSubject());
    Assertions.assertEquals("modify testModule1/NewRandomFile.java", commits.get(0).getSubject());
  }
}
