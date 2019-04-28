package io.reflectoring.coderadar.graph.projectadministration.project;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.exception.InvalidArgumentException;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.CreateProjectService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateProjectServiceTest {
  @Mock private CreateProjectRepository createProjectRepository;

  @InjectMocks private CreateProjectService createProjectService;

  @Test
  public void withInvalidArgumentShouldThrowInvalidArgumentException() {
    Assertions.assertThrows(
        InvalidArgumentException.class, () -> createProjectService.createProject(null));
  }

  @Test
  public void withValidArgumentShouldCallCreateProjectMethodOfRepository() {
    Project project = new Project();
    when(createProjectRepository.save(project)).thenReturn(project);
    createProjectService.createProject(project);
    verify(createProjectRepository, times(1)).save(project);
  }

  @Test
  public void withCreatedProjectShouldReturnProjectWithId() {
    Project mockedItem = new Project();
    mockedItem.setId(1L);
    Project newItem = new Project();
    when(createProjectRepository.save(any(Project.class))).thenReturn(mockedItem);

    Long returnedId = createProjectService.createProject(newItem);

    verify(createProjectRepository, times(1)).save(newItem);
    verifyNoMoreInteractions(createProjectRepository);
    Assertions.assertEquals(new Long(1L), returnedId);
  }
}
