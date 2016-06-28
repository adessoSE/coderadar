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
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AnalyzerConfigurationControllerTest extends ControllerTestTemplate {

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
    public void createAnalyzerConfiguration() throws Exception {

        AnalyzerConfigurationResource resource = Factories.analyzerConfigurationResource().analyzerConfiguration();
        AnalyzerConfiguration entity = Factories.analyzerConfiguration().analyzerConfiguration();

        when(analyzerConfigurationRepository.findByProjectIdAndAnalyzerName(1L, resource.getAnalyzerName())).thenReturn(entity);
        when(analyzerConfigurationRepository.save(any(AnalyzerConfiguration.class))).thenReturn(entity);
        when(analyzerRegistry.createAnalyzer(resource.getAnalyzerName())).thenReturn(new LocAnalyzerPlugin());
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
                                linkWithRel("self").description("Link to the AnalyzerConfiguration itself."),
                                linkWithRel("list").description("Link to the list of AnalyzerConfigurations for this project."),
                                linkWithRel("project").description("Link to the project to which the AnalyzerConfiguration belongs.")),
                        requestFields(
                                fields.withPath("analyzerName").description("Name of the analyzer plugin to which the AnalyzerConfiguration is applied. This should always be the fully qualified class name of the class that implements the plugin interface."),
                                fields.withPath("enabled").description("Set to TRUE if you want the analyzer plugin to be enabled and to FALSE if not. You have to specify each analyzer plugin you want to have enabled. If a project does not have a configuration for a certain plugin, that plugin is NOT enabled by default."))));

    }

    @Test
    public void listAnalyzerConfigurations() throws Exception {

        AnalyzerConfiguration config1 = Factories.analyzerConfiguration().analyzerConfiguration();
        AnalyzerConfiguration config2 = Factories.analyzerConfiguration().analyzerConfiguration2();

        when(analyzerConfigurationRepository.findByProjectId(1L)).thenReturn(Arrays.asList(config1, config2));
        when(projectRepository.countById(1L)).thenReturn(1);
        when(projectRepository.findOne(1L)).thenReturn(Factories.project().validProject());

        mvc().perform(get("/projects/1/analyzers"))
                .andExpect(status().isOk())
                .andExpect(containsList(AnalyzerConfigurationResource.class))
                .andDo(document("analyzerConfiguration/get"));
    }


    @Test
    public void getAnalyzerConfiguration() throws Exception {

        AnalyzerConfiguration config1 = Factories.analyzerConfiguration().analyzerConfiguration();

        when(analyzerConfigurationRepository.findByProjectIdAndId(1L, 1L)).thenReturn(config1);
        when(projectRepository.countById(1L)).thenReturn(1);

        mvc().perform(get("/projects/1/analyzers/1"))
                .andExpect(status().isOk())
                .andExpect(contains(AnalyzerConfigurationResource.class))
                .andDo(document("analyzerConfiguration/getSingle"));
    }

    @Test
    public void updateAnalyzerConfiguration() throws Exception {

        AnalyzerConfiguration config1 = Factories.analyzerConfiguration().analyzerConfiguration();
        Project project = Factories.project().validProject();
        AnalyzerConfigurationResource resource = Factories.analyzerConfigurationResource().analyzerConfiguration();

        when(analyzerConfigurationRepository.findByProjectIdAndId(1L, 1L)).thenReturn(config1);
        when(projectRepository.findOne(1L)).thenReturn(project);
        when(analyzerRegistry.createAnalyzer(resource.getAnalyzerName())).thenReturn(new LocAnalyzerPlugin());

        mvc().perform(post("/projects/1/analyzers/1")
                .content(toJsonWithoutLinks(resource))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(contains(AnalyzerConfigurationResource.class))
                .andDo(document("analyzerConfiguration/update"));
    }

    @Test
    public void deleteAnalyzerConfiguration() throws Exception {
        mvc().perform(delete("/projects/1/analyzers")
                .content("org.wickedsource.locAnalyzer"))
                .andExpect(status().isOk())
                .andDo(document("analyzerConfiguration/delete"));

        verify(analyzerConfigurationRepository).deleteByProjectIdAndAnalyzerName(1L, "org.wickedsource.locAnalyzer");

    }

}