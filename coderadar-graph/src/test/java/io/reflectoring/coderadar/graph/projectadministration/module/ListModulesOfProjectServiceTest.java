package io.reflectoring.coderadar.graph.projectadministration.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.exception.ProjectNotFoundException;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ListModulesOfProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.ListModulesOfProjectService;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ListModulesOfProjectServiceTest {
    @Mock
    private ListModulesOfProjectRepository listModulesOfProjectRepository;

    @Mock
    private GetProjectRepository getProjectRepository;

    @InjectMocks
    private ListModulesOfProjectService listModulesOfProjectService;

    @Test
    void withNoPersistedProjectShouldThrowProjectNotFoundException() {
        org.junit.jupiter.api.Assertions.assertThrows(
                ProjectNotFoundException.class, () -> listModulesOfProjectService.listModules(1L));
    }

    @Test
    void withNoModulesShouldReturnEmptyList() {
        Project mockedProject = new Project();
        when(getProjectRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedProject));
        when(listModulesOfProjectRepository.findByProject_Id(1L)).thenReturn(new LinkedList<>());

        Iterable<Module> modules = listModulesOfProjectService.listModules(1L);
        verify(listModulesOfProjectRepository, times(1)).findByProject_Id(1L);
        Assertions.assertThat(modules).hasSize(0);
    }

    @Test
    void withOneModuleShouldReturnListWithSizeOfOne() {
        LinkedList<Module> mockedItem = new LinkedList<>();
        mockedItem.add(new Module());
        Project mockedProject = new Project();
        when(getProjectRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedProject));
        when(listModulesOfProjectRepository.findByProject_Id(1L)).thenReturn(mockedItem);

        Iterable<Module> modules = listModulesOfProjectService.listModules(1L);
        verify(listModulesOfProjectRepository, times(1)).findByProject_Id(1L);
        Assertions.assertThat(modules).hasSize(1);
    }

    @Test
    void withTwoModulesShouldReturnListWithSizeOf() {
        LinkedList<Module> mockedItem = new LinkedList<>();
        mockedItem.add(new Module());
        mockedItem.add(new Module());
        Project mockedProject = new Project();
        when(getProjectRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedProject));
        when(listModulesOfProjectRepository.findByProject_Id(1L)).thenReturn(mockedItem);

        Iterable<Module> modules = listModulesOfProjectService.listModules(1L);
        verify(listModulesOfProjectRepository, times(1)).findByProject_Id(1L);
        Assertions.assertThat(modules).hasSize(2);
    }
}
