package io.reflectoring.coderadar.graph.projectadministration.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ListProjectsRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.ListProjectsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ListProjectsServiceTest {
    @Mock
    private ListProjectsRepository listProjectsRepository;

    @InjectMocks
    private ListProjectsService listProjectsService;

    @Test
    public void withNoProjectsShouldReturnEmptyList() {
        when(listProjectsRepository.findAll()).thenReturn(new LinkedList<>());

        Iterable<Project> projects = listProjectsService.getProjects();
        verify(listProjectsRepository, times(1)).findAll();
        Assertions.assertThat(projects).hasSize(0);
    }

    @Test
    public void withOneProjectShouldReturnListWithSizeOfOne() {
        LinkedList<Project> mockedItem = new LinkedList<>();
        mockedItem.add(new Project());
        when(listProjectsRepository.findAll()).thenReturn(mockedItem);

        Iterable<Project> projects = listProjectsService.getProjects();
        verify(listProjectsRepository, times(1)).findAll();
        Assertions.assertThat(projects).hasSize(1);
    }

    @Test
    public void withTwoProjectShouldReturnListWithSizeOfTwo() {
        LinkedList<Project> mockedItem = new LinkedList<>();
        mockedItem.add(new Project());
        mockedItem.add(new Project());
        when(listProjectsRepository.findAll()).thenReturn(mockedItem);

        Iterable<Project> projects = listProjectsService.getProjects();
        verify(listProjectsRepository, times(1)).findAll();
        Assertions.assertThat(projects).hasSize(2);
    }
}
