package org.wickedsource.coderadar;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.wickedsource.coderadar.core.rest.validation.ErrorDTO;
import org.wickedsource.coderadar.project.rest.ProjectController;
import org.wickedsource.coderadar.project.rest.ProjectResource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.wickedsource.coderadar.factories.resources.ResourceFactory.projectResource;

public class ControllerErrorsTest extends ControllerTestTemplate {

    @InjectMocks
    private ProjectController projectController;

    @Override
    protected Object getController() {
        return projectController;
    }

    @Test
    public void invalidJsonPayloadError() throws Exception {
        mvc().perform(post("/projects")
                .content("{123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(contains(ErrorDTO.class));
    }

    @Test
    public void invalidEnumConstantError() throws Exception {
        ProjectResource project = projectResource().validProjectResource();
        String projectAsJson = toJsonWithoutLinks(project);
        projectAsJson = projectAsJson.replaceAll(project.getVcsType().toString(), "ABC");

        mvc().perform(post("/projects")
                .content(projectAsJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(contains(ErrorDTO.class));
    }

    @Test
    public void invalidContentTypeError() throws Exception {
        ProjectResource project = projectResource().validProjectResource();

        mvc().perform(post("/projects")
                .content(toJsonWithoutLinks(project))
                .contentType(MediaType.APPLICATION_ATOM_XML))
                .andExpect(status().isBadRequest())
                .andExpect(contains(ErrorDTO.class));
    }

}
