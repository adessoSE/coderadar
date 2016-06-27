package org.wickedsource.coderadar.analyzer.rest;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.wickedsource.coderadar.ControllerTestTemplate;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfigurationFileRepository;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfigurationRepository;
import org.wickedsource.coderadar.factories.Factories;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.ExtendedMockHttpServletRequestBuilder.fileUploadPut;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AnalyzerConfigurationFileControllerTest extends ControllerTestTemplate {

    @InjectMocks
    private AnalyzerConfigurationFileController controller;

    @Mock
    private AnalyzerConfigurationRepository analyzerConfigurationRepository;

    @Mock
    private AnalyzerConfigurationFileRepository analyzerConfigurationFileRepository;

    @Override
    protected Object getController() {
        return controller;
    }

    @Test
    public void uploadConfigurationFile() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "configuration.xml", "text/xml", ("<configuration>" +
                "<param1>value1</param1>" +
                "<param2>value2></param2>" +
                "</configuration>").getBytes());

        when(analyzerConfigurationRepository.findByProjectIdAndId(eq(1L), eq(1L))).thenReturn(Factories.analyzerConfiguration().analyzerConfiguration());

        mvc().perform(fileUploadPut("/projects/1/analyzers/1/file")
                .file(file))
                .andExpect(status().isOk())
                .andDo(document("analyzerConfigurationFile/upload"));
    }

    @Test
    public void downloadConfigurationFile() throws Exception{

        when(analyzerConfigurationFileRepository.findByAnalyzerConfigurationProjectIdAndAnalyzerConfigurationId(eq(1L), eq(1L)))
                .thenReturn(Factories.analyzerConfiguration().analyzerConfigurationFile());

        mvc().perform(get("/projects/1/analyzers/1/file"))
                .andExpect(status().isOk())
                .andDo(document("analyzerConfigurationFile/download"));
    }
}