package org.wickedsource.coderadar.project.rest;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultHandler;
import org.wickedsource.coderadar.testframework.category.ControllerTest;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.EMPTY;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Projects.*;
import static org.wickedsource.coderadar.factories.resources.ResourceFactory.projectResource;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJson;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.validationErrorForField;

@Category(ControllerTest.class)
public class ProjectControllerTest extends ControllerTestTemplate {

    @Test
    @DatabaseSetup(EMPTY)
    @ExpectedDatabase(SINGLE_PROJECT)
    public void createProjectSuccessfully() throws Exception {
        ProjectResource project = projectResource().validProjectResource();
        mvc().perform(post("/projects")
                .content(toJsonWithoutLinks(project))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(containsResource(ProjectResource.class))
                .andDo(documentCreateProject());
    }

    private ResultHandler documentCreateProject() {
        ConstrainedFields fields = fields(ProjectResource.class);
        return document("projects/create",
                links(halLinks(),
                        linkWithRel("self").description("Link to the project."),
                        linkWithRel("files").description("Link to the project's file patterns."),
                        linkWithRel("analyzers").description("Link to the project's analyzer configurations."),
                        linkWithRel("strategy").description("Link to the project's analyzing strategy.")),
                requestFields(
                        fields.withPath("name").description("The name of the project to be analyzed."),
                        fields.withPath("vcsUrl").description("The URL to the version control repository where the project's source files are kept."),
                        fields.withPath("vcsUser").description("The user name used to access the version control system of your project. Needs read access only. Don't provide this field if anonymous access is possible."),
                        fields.withPath("vcsPassword").description("The password of the version control system user. This password has to be stored in plain text for coderadar to be usable, so make sure to provide a user with only reading permissions. Don't provide this field if anonymous access is possible."),
                        fields.withPath("vcsType").description("The type of the version control system your project uses. Either 'GIT' or 'SVN'.")));
    }

    @Test
    @DatabaseSetup(SINGLE_PROJECT)
    @ExpectedDatabase(SINGLE_PROJECT)
    public void createProjectWithValidationError() throws Exception {
        ProjectResource projectResource = projectResource().validProjectResource();
        projectResource.setName(null);
        projectResource.setVcsUrl("invalid url");

        ConstrainedFields fields = fields(ProjectResource.class);
        mvc().perform(post("/projects")
                .content(toJson(projectResource))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(validationErrorForField("name"))
                .andExpect(validationErrorForField("vcsUrl"))
                .andDo(document("projects/create/error400",
                        responseFields(
                                fields.withPath("errorMessage").description("Short message describing what went wrong. In case of validation errors, the detailed validation error messages can be found in the fieldErrors array."),
                                fields.withPath("fieldErrors").description("List of fields in the JSON payload of a request that had invalid values. May be empty. In this case, the 'message' field should contain an explanation of what went wrong."),
                                fields.withPath("fieldErrors[].field").description("Name of the field in the JSON payload of the request that had an invalid value."),
                                fields.withPath("fieldErrors[].message").description("Reason why the value is invalid."))));
    }

    @Test
    @DatabaseSetup(SINGLE_PROJECT)
    @ExpectedDatabase(SINGLE_PROJECT_2)
    public void updateProject() throws Exception {
        ProjectResource projectResource = projectResource().validProjectResource2();
        mvc().perform(post("/projects/1")
                .content(toJson(projectResource))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(containsResource(ProjectResource.class))
                .andDo(document("projects/update"));
    }

    @Test
    @DatabaseSetup(SINGLE_PROJECT)
    @ExpectedDatabase(SINGLE_PROJECT)
    public void getProjectSuccessfully() throws Exception {
        mvc().perform(get("/projects/1"))
                .andExpect(status().isOk())
                .andExpect(containsResource(ProjectResource.class))
                .andDo(document("projects/get"));
    }

    @Test
    @DatabaseSetup(SINGLE_PROJECT)
    @ExpectedDatabase(EMPTY)
    public void deleteProject() throws Exception {
        mvc().perform(delete("/projects/1"))
                .andExpect(status().isOk())
                .andDo(document("projects/delete"));
    }

    @Test
    @DatabaseSetup(PROJECT_LIST)
    @ExpectedDatabase(PROJECT_LIST)
    public void getProjectsSuccessfully() throws Exception {
        mvc().perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(containsResource(Resources.class))
                .andDo(document("projects/list"));
    }

    @Test
    @DatabaseSetup(EMPTY)
    @ExpectedDatabase(EMPTY)
    public void getProjectError() throws Exception {
        mvc().perform(get("/projects/1"))
                .andExpect(status().isNotFound())
                .andDo(document("projects/get/error404"));
    }

}