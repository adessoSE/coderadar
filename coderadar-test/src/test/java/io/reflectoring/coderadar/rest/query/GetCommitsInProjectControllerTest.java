package io.reflectoring.coderadar.rest.query;

import com.fasterxml.jackson.core.type.TypeReference;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.rest.domain.GetCommitResponse;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URL;
import java.util.List;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetCommitsInProjectControllerTest extends ControllerTestTemplate {

    private Long projectId;

    @BeforeEach
    void setUp() throws Exception {
        URL testRepoURL =  this.getClass().getClassLoader().getResource("test-repository");
        CreateProjectCommand command1 =
                new CreateProjectCommand(
                        "test-project", "username", "password", testRepoURL.toString(), false, null, null);
        MvcResult result = mvc().perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command1))).andReturn();

        projectId = fromJson(result.getResponse().getContentAsString(), IdResponse.class).getId();
    }

    @Test
    void returnsAllCommitsInProject() throws Exception {
        MvcResult result = mvc().perform(get("/projects/" + projectId + "/commits").contentType(MediaType.APPLICATION_JSON))
                .andDo(document(
                        "commit/list",
                        responseFields(
                                fieldWithPath("[]").description("Array of all the commits in this project."),
                                fieldWithPath("[].name").description("The name of the commit."),
                                fieldWithPath("[].author").description("The author of the commit"),
                                fieldWithPath("[].authorEmail").description("The email of the author"),
                                fieldWithPath("[].comment").description("The comment of this commit"),
                                fieldWithPath("[].timestamp").description("The timestamp of this commit"),
                                fieldWithPath("[].analyzed").description("Whether this commit is already analyzed or not.")
                        )))
                .andReturn();

        List<GetCommitResponse> commits = fromJson(new TypeReference<List<GetCommitResponse>>() {},
                result.getResponse().getContentAsString());

        Assertions.assertEquals(13, commits.size());
        Assertions.assertEquals("add Finding.java", commits.get(commits.size() - 1).getComment());
        Assertions.assertEquals("testCommit", commits.get(0).getComment());
    }

    @Test
    void returnsErrorWhenProjectWithIdDoesNotExist() throws Exception {
        MvcResult result = mvc().perform(get("/projects/1234/commits").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorMessageResponse response = fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class);

        Assertions.assertEquals("Project with id 1234 not found.", response.getErrorMessage());
    }
}
