package org.wickedsource.coderadar.analyzingstrategy.rest;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.wickedsource.coderadar.ControllerTestTemplate;
import org.wickedsource.coderadar.analyzingstrategy.domain.AnalyzingStrategyRepository;
import org.wickedsource.coderadar.factories.Factories;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AnalyzingStrategyControllerTest extends ControllerTestTemplate {

    @Mock
    private ProjectVerifier projectVerifier;

    @Mock
    private AnalyzingStrategyRepository analyzingStrategyRepository;

    @InjectMocks
    private AnalyzingStrategyController controller;

    @Test
    public void setAnalyzingStrategy() throws Exception {
        AnalyzingStrategyResource resource = Factories.analyzingStrategy().analyzingStrategyResource();

        ConstrainedFields fields = fields(AnalyzingStrategyResource.class);
        mvc().perform(post("/projects/1/strategy")
                .content(toJsonWithoutLinks(resource))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(contains(AnalyzingStrategyResource.class))
                .andDo(document("strategy/set",
                        links(atomLinks(),
                                linkWithRel("self").description("Link to this AnalyzingStrategy resource."),
                                linkWithRel("project").description("Link to the project resource this AnalyzingStrategy belongs to.")),
                        requestFields(
                                fields.withPath("active").description("Defines if this analyzing strategy is active. If it is not active (or there is no strategy for the project at all), no commits of the project will be analyzed. Analyses of commits that are already queued will be finished, however."),
                                fields.withPath("rescan").description("Set this to true when updating an AnalyzingStrategy if you want to delete all existing analysis results and restart analysis with the new strategy. You will get an error response if you try rescanning a project while there are running or queued analysis jobs (i.e. when the previous scan over all commits is not yet finished)."),
                                fields.withPath("fromDate").description("Date (in milliseconds since epoch) from which to start analyzing commits. Commits before this date are ignored during analysis. If no date is specified, all commits will be analyzed."))));

    }

    @Test
    public void getAnalyzingStrategy() throws Exception {

        when(analyzingStrategyRepository.findByProjectId(1L)).thenReturn(Factories.analyzingStrategy().analyzingStrategy());

        mvc().perform(get("/projects/1/strategy"))
                .andExpect(status().isOk())
                .andExpect(contains(AnalyzingStrategyResource.class))
                .andDo(document("strategy/get"));
    }


    @Override
    protected Object getController() {
        return controller;
    }
}