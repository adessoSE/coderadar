package org.wickedsource.coderadar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.wickedsource.coderadar.factories.resources.ResourceFactory.projectResource;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.wickedsource.coderadar.core.rest.validation.ErrorDTO;
import org.wickedsource.coderadar.project.rest.ProjectResource;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

public class ControllerErrorsTest extends ControllerTestTemplate {

  @Test
  public void invalidJsonPayloadError() throws Exception {
    mvc()
        .perform(post("/projects").content("{123").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(containsResource(ErrorDTO.class));
  }

  @Test
  public void invalidContentTypeError() throws Exception {
    ProjectResource project = projectResource().validProjectResource();

    mvc()
        .perform(
            post("/projects")
                .content(toJsonWithoutLinks(project))
                .contentType(MediaType.APPLICATION_ATOM_XML))
        .andExpect(status().isBadRequest())
        .andExpect(containsResource(ErrorDTO.class));
  }
}
