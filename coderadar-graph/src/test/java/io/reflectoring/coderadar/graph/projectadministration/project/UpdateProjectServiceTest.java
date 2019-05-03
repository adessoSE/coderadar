package io.reflectoring.coderadar.graph.projectadministration.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.exception.ProjectNotFoundException;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.UpdateProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.UpdateProjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UpdateProjectServiceTest {
    @Mock
    private GetProjectRepository getProjectRepository;

    @Mock
    private UpdateProjectRepository updateProjectRepository;

    @InjectMocks
    private UpdateProjectService updateProjectService;

    @Test
    public void withInvalidProjectIdShouldThrowProjectNotFoundException() {
        Assertions.assertThrows(
                ProjectNotFoundException.class, () -> updateProjectService.update(new Project()));
    }

    @Test
    public void withValidArgumentShouldCallMethodOfRepositoryToGetOldProject() {
        Project mockedItem = new Project();
        mockedItem.setId(1L);
        when(getProjectRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedItem));

        Project project = new Project();
        project.setId(1L);
        updateProjectService.update(project);

        verify(getProjectRepository, times(1)).findById(1L);
    }

    @Test
    public void withValidArgumentShouldCallMethodOfRepositoryToUpdateProject() {
        Project mockedOldItem = new Project();
        mockedOldItem.setId(1L);
        mockedOldItem.setName("Mustermann");
        when(getProjectRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedOldItem));

        Project mockedItem = new Project();
        mockedItem.setId(1L);
        mockedItem.setName("Musterfrau");
        when(updateProjectRepository.save(any(Project.class))).thenReturn(mockedItem);

        Project project = new Project();
        project.setId(1L);
        project.setName("Musterfrau");
        updateProjectService.update(project);

        verify(updateProjectRepository, times(1)).save(mockedItem);
        Assertions.assertNotEquals(mockedOldItem, project);
    }
}
