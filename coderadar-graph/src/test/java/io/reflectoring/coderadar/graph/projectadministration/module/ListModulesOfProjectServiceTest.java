package io.reflectoring.coderadar.graph.projectadministration.module;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ListModulesOfProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.ListModulesOfProjectService;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import java.util.LinkedList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("List modules of project")
class ListModulesOfProjectServiceTest {
  private ListModulesOfProjectRepository listModulesOfProjectRepository =
      mock(ListModulesOfProjectRepository.class);

  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);

  private ListModulesOfProjectService listModulesOfProjectService;

  @BeforeEach
  void setUp() {
    listModulesOfProjectService =
        new ListModulesOfProjectService(getProjectRepository, listModulesOfProjectRepository);
  }

  @Test
  @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
    org.junit.jupiter.api.Assertions.assertThrows(
        ProjectNotFoundException.class, () -> listModulesOfProjectService.listModules(1L));
  }

  @Test
  @DisplayName("Should return empty list when no modules in the project exist")
  void shouldReturnEmptyListWhenNoModulesInTheProjectExist() {
    Project mockedProject = new Project();
    when(getProjectRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedProject));
    when(listModulesOfProjectRepository.findByProjectId(1L)).thenReturn(new LinkedList<>());

    Iterable<Module> modules = listModulesOfProjectService.listModules(1L);
    verify(listModulesOfProjectRepository, times(1)).findByProjectId(1L);
    Assertions.assertThat(modules).hasSize(0);
  }

  @Test
  @DisplayName("Should return list with size of one when one module in the project exists")
  void shouldReturnListWithSizeOfOneWhenOneModuleInTheProjectExists() {
    LinkedList<Module> mockedItem = new LinkedList<>();
    mockedItem.add(new Module());
    Project mockedProject = new Project();
    when(getProjectRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedProject));
    when(listModulesOfProjectRepository.findByProjectId(1L)).thenReturn(mockedItem);

    Iterable<Module> modules = listModulesOfProjectService.listModules(1L);
    verify(listModulesOfProjectRepository, times(1)).findByProjectId(1L);
    Assertions.assertThat(modules).hasSize(1);
  }

  @Test
  @DisplayName("Should return list with size of two when two modules in the project exist")
  void shouldReturnListWithSizeOfTwoWhenTwoModulesInTheProjectExist() {
    LinkedList<Module> mockedItem = new LinkedList<>();
    mockedItem.add(new Module());
    mockedItem.add(new Module());
    Project mockedProject = new Project();
    when(getProjectRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedProject));
    when(listModulesOfProjectRepository.findByProjectId(1L)).thenReturn(mockedItem);

    Iterable<Module> modules = listModulesOfProjectService.listModules(1L);
    verify(listModulesOfProjectRepository, times(1)).findByProjectId(1L);
    Assertions.assertThat(modules).hasSize(2);
  }
}
