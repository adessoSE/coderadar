package io.reflectoring.coderadar.graph.projectadministration.project;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.CreateProjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Create project")
class CreateProjectServiceTest {
  private CreateProjectRepository createProjectRepository = mock(CreateProjectRepository.class);

  private CreateProjectService createProjectService;

  @BeforeEach
  void setUp() {
    createProjectService = new CreateProjectService(createProjectRepository);
  }

  @Test
  @DisplayName("Should save project when passing a valid project entity")
  void shouldSaveProjectWhenPassingAValidProjectEntity() {
    Project project = new Project();
    when(createProjectRepository.save(project)).thenReturn(project);

    createProjectService.createProject(project);

    verify(createProjectRepository, times(1)).save(project);
  }

  @Test
  @DisplayName("Should return ID when saving project")
  void shouldReturnIdWhenSavingProject() {
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
