package org.wickedsource.coderadar.analyzingstrategy.rest;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.http.MediaType;
import org.wickedsource.coderadar.ControllerTest;
import org.wickedsource.coderadar.ControllerTestTemplate;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.AnalyzingStrategies.SINGLE_PROJECT_WITH_ANALYZING_STRATEGY;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.EMPTY;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Projects.SINGLE_PROJECT;
import static org.wickedsource.coderadar.factories.resources.ResourceFactory.analyzingStrategyResource;

@Category(ControllerTest.class)
public class AnalyzingStrategyControllerTest extends ControllerTestTemplate {

    @Test
    @DatabaseSetup(SINGLE_PROJECT)
    @ExpectedDatabase(SINGLE_PROJECT_WITH_ANALYZING_STRATEGY)
    public void setAnalyzingStrategy() throws Exception {
        AnalyzingStrategyResource resource = analyzingStrategyResource().analyzingStrategyResource();

        ConstrainedFields fields = fields(AnalyzingStrategyResource.class);
        mvc().perform(post("/projects/1/strategy")
                .content(toJsonWithoutLinks(resource))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(contains(AnalyzingStrategyResource.class))
                .andDo(document("strategy/create-update",
                        links(halLinks(),
                                linkWithRel("self").description("Link to this AnalyzingStrategy resource."),
                                linkWithRel("project").description("Link to the project resource this AnalyzingStrategy belongs to.")),
                        requestFields(
                                fields.withPath("active").description("Defines if this analyzing strategy is active. If it is not active (or there is no strategy for the project at all), no commits of the project will be analyzed. Analyses of commits that are already queued will be finished, however."),
                                fields.withPath("rescan").description("Set this to true when updating an AnalyzingStrategy if you want to delete all existing analysis results and restart analysis with the new strategy. You will get an error response if you try rescanning a project while there are running or queued analysis jobs (i.e. when the previous scan over all commits is not yet finished)."),
                                fields.withPath("fromDate").description("Date (in milliseconds since epoch) from which to start analyzing commits. Commits before this date are ignored during analysis. If no date is specified, all commits will be analyzed."))));

    }

    @Test
    @DatabaseSetup(EMPTY)
    @ExpectedDatabase(EMPTY)
    public void setAnalyzingStrategyProjectNotFound() throws Exception {
        AnalyzingStrategyResource resource = analyzingStrategyResource().analyzingStrategyResource();
        mvc().perform(post("/projects/1/strategy")
                .content(toJsonWithoutLinks(resource))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DatabaseSetup(SINGLE_PROJECT_WITH_ANALYZING_STRATEGY)
    @ExpectedDatabase(SINGLE_PROJECT_WITH_ANALYZING_STRATEGY)
    public void getAnalyzingStrategy() throws Exception {
        mvc().perform(get("/projects/1/strategy"))
                .andExpect(status().isOk())
                .andExpect(contains(AnalyzingStrategyResource.class))
                .andDo(document("strategy/get"));
    }

    @Test
    @DatabaseSetup(SINGLE_PROJECT)
    @ExpectedDatabase(SINGLE_PROJECT)
    public void getNonExistingAnalyzingStrategy() throws Exception {
        mvc().perform(get("/projects/1/strategy"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DatabaseSetup(EMPTY)
    @ExpectedDatabase(EMPTY)
    public void getAnalyzingStrategyProjectNotFound() throws Exception {
        mvc().perform(get("/projects/1/strategy"))
                .andExpect(status().isNotFound());
    }

}