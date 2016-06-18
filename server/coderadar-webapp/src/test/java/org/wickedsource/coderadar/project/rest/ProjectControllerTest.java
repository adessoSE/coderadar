package org.wickedsource.coderadar.project.rest;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;
import org.wickedsource.coderadar.ControllerTestTemplate;
import org.wickedsource.coderadar.factories.Factories;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectControllerTest extends ControllerTestTemplate {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectRepository projectRepository;

    @Spy
    private ProjectResourceAssembler projectAssembler;

    @Override
    protected ProjectController getController() {
        return projectController;
    }

    @Test
    public void createProjectSuccessfully() throws Exception {
        ProjectResource project = Factories.projectResource().validProjectResource();

        when(projectRepository.save(any(Project.class))).thenReturn(Factories.project().validProject());

        mvc().perform(post("/projects")
                .content(toJsonWithoutLinks(project))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(containsProjectResource())
                .andDo(documentCreateProject());
    }

    private ResultHandler documentCreateProject() {
        ConstrainedFields fields = fields(ProjectResource.class);
        return document("projects/post",
                links(atomLinks(),
                        linkWithRel("self").description("Link to the project resource just created."),
                        linkWithRel("files").description("Link to the project's file patterns.")),
                requestFields(
                        fields.withPath("name").description("The name of the project to be analyzed."),
                        fields.withPath("vcsUrl").description("The URL to the version control repository where the project's source files are kept."),
                        fields.withPath("vcsUser").description("The user name used to access the version control system of your project. Needs read access only. Don't provide this field if anonymous access is possible."),
                        fields.withPath("vcsPassword").description("The password of the version control system user. This password has to be stored in plain text for coderadar to be usable, so make sure to provide a user with only reading permissions. Don't provide this field if anonymous access is possible."),
                        fields.withPath("vcsType").description("The type of the version control system your project uses. Either 'GIT' or 'SVN'.")));
    }

    @Test
    public void createProjectWithValidationError() throws Exception {
        ProjectResource projectResource = Factories.projectResource().validProjectResource();
        projectResource.setName(null);
        projectResource.setVcsUrl("invalid url");

        ConstrainedFields fields = fields(ProjectResource.class);

        mvc().perform(post("/projects")
                .content(toJson(projectResource))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(validationErrorForField("name"))
                .andExpect(validationErrorForField("vcsUrl"))
                .andDo(document("projects/post/error400",
                        responseFields(
                           fields.withPath("fieldErrors").description("List of fields in the JSON payload of a request that had invalid values. May be empty. In this case, the 'message' field should contain an explanation of what went wrong."),
                           fields.withPath("fieldErrors[].field").description("Name of the field in the JSON payload of the request that had an invalid value."),
                                fields.withPath("fieldErrors[].message").description("Reason why the value is invalid."))));
    }

    @Test
    public void getProjectSuccessfully() throws Exception {
        Project project = new Project();
        project.setId(5L);
        when(projectRepository.findOne(5L)).thenReturn(project);
        mvc().perform(get("/projects/5"))
                .andExpect(status().isOk())
                .andDo(document("projects/get"));
    }

    @Test
    public void getProjectsSuccessfully() throws Exception {
        List<Project> projects = new ArrayList<>();
        projects.add(Factories.project().validProject());
        projects.add(Factories.project().validProject2());
        when(projectRepository.findAll()).thenReturn(projects);
        mvc().perform(get("/projects"))
                .andExpect(status().isOk())
                .andDo(document("projects/list"));
    }

    @Test
    public void getProjectError() throws Exception {
        mvc().perform(get("/projects/1"))
                .andExpect(status().isNotFound())
                .andDo(document("projects/get/error404"));
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