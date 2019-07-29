package io.reflectoring.coderadar.graph.projectadministration.module;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ListModulesOfProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.ListModulesOfProjectAdapter;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.mockito.Mockito.*;

@DisplayName("List modules of project")
class ListModulesOfProjectAdapterTest {
  private ListModulesOfProjectRepository listModulesOfProjectRepository =
      mock(ListModulesOfProjectRepository.class);

  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);

  private ListModulesOfProjectAdapter listModulesOfProjectAdapter;

  @BeforeEach
  void setUp() {
    listModulesOfProjectAdapter =
        new ListModulesOfProjectAdapter(getProjectRepository, listModulesOfProjectRepository);
  }

  @Test
  @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
    org.junit.jupiter.api.Assertions.assertThrows(
        ProjectNotFoundException.class, () -> listModulesOfProjectAdapter.listModules(1L));
  }

  @Test
  @DisplayName("Should return empty list when no modules in the project exist")
  void shouldReturnEmptyListWhenNoModulesInTheProjectExist() {
    ProjectEntity mockedProject = new ProjectEntity();
    when(getProjectRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedProject));
    when(listModulesOfProjectRepository.findModulesInProject(1L)).thenReturn(new LinkedList<>());

    Iterable<Module> modules = listModulesOfProjectAdapter.listModules(1L);
    verify(listModulesOfProjectRepository, times(1)).findModulesInProject(1L);
    Assertions.assertThat(modules).hasSize(0);
  }

  @Test
  @DisplayName("Should return list with size of one when one module in the project exists")
  void shouldReturnListWithSizeOfOneWhenOneModuleInTheProjectExists() {
    LinkedList<ModuleEntity> mockedItem = new LinkedList<>();
    mockedItem.add(new ModuleEntity());
    ProjectEntity mockedProject = new ProjectEntity();
    when(getProjectRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedProject));
    when(listModulesOfProjectRepository.findModulesInProject(1L)).thenReturn(mockedItem);

    Iterable<Module> modules = listModulesOfProjectAdapter.listModules(1L);
    verify(listModulesOfProjectRepository, times(1)).findModulesInProject(1L);
    Assertions.assertThat(modules).hasSize(1);
  }

  @Test
  @DisplayName("Should return list with size of two when two modules in the project exist")
  void shouldReturnListWithSizeOfTwoWhenTwoModulesInTheProjectExist() {
    LinkedList<ModuleEntity> mockedItem = new LinkedList<>();
    mockedItem.add(new ModuleEntity());
    mockedItem.add(new ModuleEntity());
    ProjectEntity mockedProject = new ProjectEntity();
    when(getProjectRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedProject));
    when(listModulesOfProjectRepository.findModulesInProject(1L)).thenReturn(mockedItem);

    Iterable<Module> modules = listModulesOfProjectAdapter.listModules(1L);
    verify(listModulesOfProjectRepository, times(1)).findModulesInProject(1L);
    Assertions.assertThat(modules).hasSize(2);
  }
}
