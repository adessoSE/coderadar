package org.wickedsource.coderadar.project.rest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.wickedsource.coderadar.ControllerTestTemplate;
import org.wickedsource.coderadar.core.rest.validation.ValidationExceptionHandler;
import org.wickedsource.coderadar.factories.Factories;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
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

    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(projectController)
                .setControllerAdvice(new ValidationExceptionHandler())
                .build();
    }

    @Test
    public void createProjectSuccessfully() throws Exception {
        ProjectResource project = Factories.projectResource().validProjectResource();

        when(projectRepository.save(any(Project.class))).thenReturn(Factories.project().validProject());

        mvc.perform(post("/projects")
                .content(toJson(project))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(containsProjectResource());
    }

    @Test
    public void createProjectWithValidationError() throws Exception {
        ProjectResource projectResource = Factories.projectResource().validProjectResource();
        projectResource.setName(null);
        projectResource.setVcsUrl("invalid url");

        mvc.perform(post("/projects")
                .content(toJson(projectResource))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(validationErrorForField("name"))
                .andExpect(validationErrorForField("vcsUrl"));
    }

    @Test
    public void getProjectSuccessfully() throws Exception {
        Project project = new Project();
        project.setId(5L);
        when(projectRepository.findOne(5L)).thenReturn(project);
        mvc.perform(get("/projects/5"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProjectError() throws Exception {
        mvc.perform(get("/projects/1"))
                .andExpect(status().isNotFound());
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