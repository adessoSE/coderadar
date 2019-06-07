package io.reflectoring.coderadar.graph.projectadministration.project;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.ListProjectsRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.ListProjectsAdapter;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import java.util.LinkedList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("List projects")
class ListProjectsAdapterTest {
  private ListProjectsRepository listProjectsRepository = mock(ListProjectsRepository.class);

  private ListProjectsAdapter listProjectsAdapter;

  @BeforeEach
  void setUp() {
    listProjectsAdapter = new ListProjectsAdapter(listProjectsRepository);
  }

  @Test
  @DisplayName("Should return empty list when no projects exist")
  void shouldReturnEmptyListWhenNoProjectsExist() {
    when(listProjectsRepository.findAll()).thenReturn(new LinkedList<>());

    Iterable<Project> projects = listProjectsAdapter.getProjects();
    verify(listProjectsRepository, times(1)).findAll();
    Assertions.assertThat(projects).hasSize(0);
  }

  @Test
  @DisplayName("Should return list with size of one when one project exists")
  void shouldReturnListWithSizeOfOneWhenOneProjectExists() {
    LinkedList<Project> mockedItem = new LinkedList<>();
    mockedItem.add(new Project());
    when(listProjectsRepository.findAll()).thenReturn(mockedItem);

    Iterable<Project> projects = listProjectsAdapter.getProjects();
    verify(listProjectsRepository, times(1)).findAll();
    Assertions.assertThat(projects).hasSize(1);
  }

  @Test
  @DisplayName("Should return list with size of two when two projects exist")
  void shouldReturnListWithSizeOfTwoWhenTwoProjectsExist() {
    LinkedList<Project> mockedItem = new LinkedList<>();
    mockedItem.add(new Project());
    mockedItem.add(new Project());
    when(listProjectsRepository.findAll()).thenReturn(mockedItem);

    Iterable<Project> projects = listProjectsAdapter.getProjects();
    verify(listProjectsRepository, times(1)).findAll();
    Assertions.assertThat(projects).hasSize(2);
  }
}
