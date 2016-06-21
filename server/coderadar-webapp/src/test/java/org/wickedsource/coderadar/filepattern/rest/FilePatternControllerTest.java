package org.wickedsource.coderadar.filepattern.rest;


import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.wickedsource.coderadar.ControllerTestTemplate;
import org.wickedsource.coderadar.factories.Factories;
import org.wickedsource.coderadar.filepattern.domain.FilePatternRepository;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FilePatternControllerTest extends ControllerTestTemplate {

    @InjectMocks
    private FilePatternController filePatternController;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private FilePatternRepository filePatternRepository;

    @Override
    protected Object getController() {
        return filePatternController;
    }

    @Test
    public void setFilePatterns() throws Exception {
        FilePatternResource filepatterns = Factories.filePatternResource().filePatterns();

        when(projectRepository.findOne(1L)).thenReturn(Factories.project().validProject());
        when(filePatternRepository.save(any(List.class))).thenReturn(Arrays.asList(
                Factories.filePattern().filePattern(),
                Factories.filePattern().filePattern2()));

        ConstrainedFields fields = fields(FilePatternResource.class);

        mvc().perform(post("/projects/1/files")
                .content(toJsonWithoutLinks(filepatterns))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(contains(FilePatternResource.class))
                .andDo(document("filepatterns/post",
                        links(atomLinks(),
                                linkWithRel("self").description("Link to GET the list of file patterns of a project."),
                                linkWithRel("project").description("Link to GET the project these file patterns belong to.")),
                        requestFields(
                                fields.withPath("filePatterns[].pattern").description("Ant-style file pattern matching a set of files in the project's code base."),
                                fields.withPath("filePatterns[].inclusionType").description("Either 'INCLUDE' if the matching set of files is to be included or 'EXCLUDE' if it is to be excluded."),
                                fields.withPath("filePatterns[].fileSet").description("The set of files the files matching this pattern belong to. Currently, the only available value is 'SOURCE' describing source code files."))));
    }

    @Test
    public void setFilePatternsWithInvalidProject() throws Exception {
        FilePatternResource filepatterns = Factories.filePatternResource().filePatterns();
        when(projectRepository.findOne(1L)).thenReturn(null);
        mvc().perform(post("/projects/1/files")
                .content(toJsonWithoutLinks(filepatterns))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getFilePatterns() throws Exception {
        FilePatternResource filepatterns = Factories.filePatternResource().filePatterns();

        when(projectRepository.countById(1L)).thenReturn(1);
        when(filePatternRepository.save(any(List.class))).thenReturn(Arrays.asList(
                Factories.filePattern().filePattern(),
                Factories.filePattern().filePattern2()));

        mvc().perform(get("/projects/1/files")
                .content(toJsonWithoutLinks(filepatterns))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(contains(FilePatternResource.class))
                .andDo(document("filepatterns/get"));
    }

    @Test
    public void getFilePatternsWithInvalidProject() throws Exception {
        FilePatternResource filepatterns = Factories.filePatternResource().filePatterns();
        when(projectRepository.countById(1L)).thenReturn(0);
        mvc().perform(get("/projects/1/files")
                .content(toJsonWithoutLinks(filepatterns))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}