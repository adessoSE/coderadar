package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.ListProjectsUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class ListProjectsControllerTest {

    @Mock
    private ListProjectsUseCase listProjectsUseCase;
    private ListProjectsController testSubject;

    @BeforeEach
    public void setup() {
        testSubject = new ListProjectsController(listProjectsUseCase);
    }

    @Test
    public void listAllProjects() {
        Project project1 = new Project();
        Project project2 = new Project();
        Project project3 = new Project();

        List<Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);

        Mockito.when(listProjectsUseCase.listProjects()).thenReturn(projects);

        ResponseEntity<List<Project>> responseEntity = testSubject.listProjects();

        Assertions.assertEquals(3L, responseEntity.getBody().size());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
