package org.wickedsource.coderadar.module.rest;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Modules.*;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Projects.SINGLE_PROJECT;
import static org.wickedsource.coderadar.factories.resources.ModuleResourceFactory.module;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.*;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

@Tag("ControllerTest.class")
public class ModuleControllerTest extends ControllerTestTemplate {

  @Test
  @DatabaseSetup(SINGLE_PROJECT)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_MODULE)
  public void createModule() throws Exception {
    ConstrainedFields fields = fields(ModuleResource.class);

    ModuleResource resource = module();
    mvc()
        .perform(
            post("/projects/1/modules")
                .content(toJsonWithoutLinks(resource))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(containsResource(ModuleResource.class))
        .andDo(
            document(
                "modules/create",
                links(
                    halLinks(),
                    linkWithRel("self").description("Link to this module."),
                    linkWithRel("project")
                        .description("Link to the project this module belongs to.")),
                requestFields(
                    fields
                        .withPath("modulePath")
                        .description(
                            "The path of this module starting at the VCS root. All files below that path are considered to be part of the module."))));
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_MODULE)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_MODULE2)
  public void updateModule() throws Exception {
    ModuleResource resource = module();
    resource.setModulePath("server/coderadar-server");
    mvc()
        .perform(
            post("/projects/1/modules/1")
                .content(toJsonWithoutLinks(resource))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(containsResource(ModuleResource.class))
        .andDo(document("modules/update"));
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_MODULE)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_MODULE)
  public void getModule() throws Exception {
    mvc()
        .perform(get("/projects/1/modules/1"))
        .andExpect(status().isOk())
        .andExpect(containsResource(ModuleResource.class))
        .andDo(document("modules/get"));
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_MODULES)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_MODULES)
  public void listModules() throws Exception {
    mvc()
        .perform(get("/projects/1/modules"))
        .andExpect(status().isOk())
        .andExpect(containsPagedResources(ModuleResource.class))
        .andDo(document("modules/list"));
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_MODULE)
  @ExpectedDatabase(SINGLE_PROJECT)
  public void deleteModule() throws Exception {
    mvc()
        .perform(delete("/projects/1/modules/1"))
        .andExpect(status().isOk())
        .andDo(document("modules/delete"));
  }
}
