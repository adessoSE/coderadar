package org.wickedsource.coderadar.analyzer.rest.analyzerconfiguration;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.wickedsource.coderadar.testframework.category.ControllerTest;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.AnalyzerConfiguration.*;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.EMPTY;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Projects.SINGLE_PROJECT;
import static org.wickedsource.coderadar.factories.resources.ResourceFactory.analyzerConfigurationResource;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

@Category(ControllerTest.class)
public class AnalyzerConfigurationControllerTest extends ControllerTestTemplate {

    @Test
    @DatabaseSetup(SINGLE_PROJECT)
    @ExpectedDatabase(SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION)
    public void createAnalyzerConfiguration() throws Exception {
        AnalyzerConfigurationResource resource = analyzerConfigurationResource().analyzerConfiguration();
        ConstrainedFields<AnalyzerConfigurationResource> fields = fields(AnalyzerConfigurationResource.class);
        mvc().perform(post("/projects/1/analyzers")
                .content(toJsonWithoutLinks(resource))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(containsResource(AnalyzerConfigurationResource.class))
                .andDo(document("analyzerConfiguration/post",
                        links(halLinks(),
                                linkWithRel("self").description("Link to the AnalyzerConfiguration itself."),
                                linkWithRel("list").description("Link to the list of AnalyzerConfigurations for this project."),
                                linkWithRel("project").description("Link to the project to which the AnalyzerConfiguration belongs.")),
                        requestFields(
                                fields.withPath("analyzerName").description("Name of the analyzer plugin to which the AnalyzerConfiguration is applied. This should always be the fully qualified class name of the class that implements the plugin interface."),
                                fields.withPath("enabled").description("Set to TRUE if you want the analyzer plugin to be enabled and to FALSE if not. You have to specify each analyzer plugin you want to have enabled. If a project does not have a configuration for a certain plugin, that plugin is NOT enabled by default."))));
    }

    @Test
    @DatabaseSetup(SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION_LIST)
    @ExpectedDatabase(SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION_LIST)
    public void listAnalyzerConfigurations() throws Exception {
        mvc().perform(get("/projects/1/analyzers"))
                .andExpect(status().isOk())
                .andExpect(containsResource(Resources.class))
                .andDo(document("analyzerConfiguration/get"));
    }


    @Test
    @DatabaseSetup(SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION_LIST)
    @ExpectedDatabase(SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION_LIST)
    public void getAnalyzerConfiguration() throws Exception {
        mvc().perform(get("/projects/1/analyzers/1"))
                .andExpect(status().isOk())
                .andExpect(containsResource(AnalyzerConfigurationResource.class))
                .andDo(document("analyzerConfiguration/getSingle"));
    }

    @Test
    @DatabaseSetup(SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION)
    @ExpectedDatabase(SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION2)
    public void updateAnalyzerConfiguration() throws Exception {
        AnalyzerConfigurationResource resource = analyzerConfigurationResource().analyzerConfiguration2();
        mvc().perform(post("/projects/1/analyzers/1")
                .content(toJsonWithoutLinks(resource))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(containsResource(AnalyzerConfigurationResource.class))
                .andDo(document("analyzerConfiguration/update"));
    }

    @Test
    @DatabaseSetup(SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION_LIST)
    @ExpectedDatabase(SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION)
    public void deleteAnalyzerConfiguration() throws Exception {
        mvc().perform(delete("/projects/1/analyzers/2"))
                .andExpect(status().isOk())
                .andDo(document("analyzerConfiguration/delete"));
    }

    @Test
    @DatabaseSetup(EMPTY)
    @ExpectedDatabase(EMPTY)
    public void deleteAnalyzerConfigurationProjectNotFound() throws Exception {
        mvc().perform(delete("/projects/1/analyzers/2"))
                .andExpect(status().isNotFound());
    }

}