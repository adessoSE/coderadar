package org.wickedsource.coderadar.analyzer.rest;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.wickedsource.coderadar.ControllerTestTemplate;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfiguration;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfigurationRepository;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerPluginRegistry;
import org.wickedsource.coderadar.analyzer.loc.LocAnalyzerPlugin;
import org.wickedsource.coderadar.factories.Factories;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AnalyzerConfigurationControllerTest extends ControllerTestTemplate{

    @InjectMocks
    private AnalyzerConfigurationController controller;

    @Mock
    private AnalyzerConfigurationRepository analyzerConfigurationRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private AnalyzerPluginRegistry analyzerRegistry;


    @Override
    protected Object getController() {
        return controller;
    }

    @Test
    public void createAnalyzerConfiguration() throws Exception{

        AnalyzerConfigurationResource resource = Factories.analyzerConfigurationResource().analyzerConfiguration();
        AnalyzerConfiguration entity = Factories.analyzerConfiguration().analyzerConfiguration();

        when(analyzerConfigurationRepository.findByProjectIdAndAnalyzerName(1L, resource.getAnalyzerName())).thenReturn(entity);
        when(analyzerConfigurationRepository.save(entity)).thenReturn(entity);
        when(analyzerRegistry.getAnalyzer(resource.getAnalyzerName())).thenReturn(new LocAnalyzerPlugin());
        when(projectRepository.countById(1L)).thenReturn(1);
        when(projectRepository.findOne(1L)).thenReturn(Factories.project().validProject());

        ConstrainedFields<AnalyzerConfigurationResource> fields = fields(AnalyzerConfigurationResource.class);

        mvc().perform(post("/projects/1/analyzers")
                .content(toJsonWithoutLinks(resource))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(contains(AnalyzerConfigurationResource.class))
                .andDo(document("analyzerConfiguration/post",
                        links(atomLinks(),
                                linkWithRel("list").description("Link to GET the list of AnalyzerConfigurations for this project."),
                                linkWithRel("project").description("Link to GET the project to which the AnalyzerConfiguration belongs.")),
                        requestFields(
                                fields.withPath("analyzerName").description("Name of the analyzer plugin to which the AnalyzerConfiguration is applied. This should always be the fully qualified class name of the class that implements the plugin interface."),
                                fields.withPath("enabled").description("Set to TRUE if you want the analyzer plugin to be enabled and to FALSE if not. You have to specify each analyzer plugin you want to have enabled. If a project does not have a configuration for a certain plugin, that plugin is NOT enabled by default."))));

    }

}