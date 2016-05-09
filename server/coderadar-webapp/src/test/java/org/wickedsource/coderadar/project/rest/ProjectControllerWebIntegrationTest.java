package org.wickedsource.coderadar.project.rest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.wickedsource.coderadar.WebIntegrationTestTemplate;
import org.wickedsource.coderadar.project.domain.VcsType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectControllerWebIntegrationTest extends WebIntegrationTestTemplate {

    @Test
    public void createProjectSuccessfully() throws Exception {
        ProjectResource project = createValidProjectResource();

        mvc.perform(post("/projects")
                .content(toJson(project))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(containsProjectResource());
    }

    @Test
    public void createProjectValidationError() throws Exception {
        ProjectResource project = createValidProjectResource();
        project.setName(null);
        project.setVcsUrl("invalid url");
        project.setEntityId(-1L);

        mvc.perform(post("/projects")
                .content(toJson(project))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(validationErrorForField("name"))
                .andExpect(validationErrorForField("vcsUrl"))
                .andExpect(validationErrorForField("entityId"));
    }

    private ProjectResource createValidProjectResource() {
        ProjectResource project = new ProjectResource();
        project.setEntityId(1L);
        project.setVcsUser("user");
        project.setVcsPassword("pass");
        project.setVcsUrl("http://valid.url");
        project.setName("name");
        project.setVcsType(VcsType.GIT);
        return project;
    }

    private ResultMatcher containsProjectResource() {
        return result -> {
            String json = result.getResponse().getContentAsString();
            try {
                ProjectResource project = fromJson(json, ProjectResource.class);
                Assert.assertNotNull(project);
            } catch (Exception e) {
                Assert.fail(String.format("expected JSON representation of ProjectResource but found '%s'", json));
            }
        };
    }

}