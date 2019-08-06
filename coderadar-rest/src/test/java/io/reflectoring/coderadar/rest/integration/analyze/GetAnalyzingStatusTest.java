package io.reflectoring.coderadar.rest.integration.analyze;

import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzingJobEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.GetAnalyzingStatusRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class GetAnalyzingStatusTest extends ControllerTestTemplate {
    @Autowired
    private CreateProjectRepository createProjectRepository;

    @Autowired
    private GetAnalyzingStatusRepository getAnalyzingStatusRepository;

    @Test
    public void testReturnsTrueWhenAnalyzingProject() throws Exception {
        ProjectEntity testProject = new ProjectEntity();
        FileEntity fileEntity = new FileEntity();
        fileEntity.setPath("module-path/Main.java");
        testProject.setVcsUrl("https://valid.url");
        testProject.getFiles().add(fileEntity);
        testProject = createProjectRepository.save(testProject);

        AnalyzingJobEntity analyzingJobEntity = new AnalyzingJobEntity();
        analyzingJobEntity.setActive(true);
        analyzingJobEntity.setFrom(new Date());
        analyzingJobEntity.setProject(testProject);
        analyzingJobEntity.setRescan(true);
        getAnalyzingStatusRepository.save(analyzingJobEntity);

        mvc()
                .perform(
                        get("/projects/" + testProject.getId() + "/analyzingStatus"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("true"));
    }
}
