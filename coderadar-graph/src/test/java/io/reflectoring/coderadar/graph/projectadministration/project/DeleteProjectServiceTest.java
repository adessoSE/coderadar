package io.reflectoring.coderadar.graph.projectadministration.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.DeleteProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.DeleteProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Delete project")
class DeleteProjectServiceTest {
    @Mock
    private DeleteProjectRepository deleteProjectRepository;

    @InjectMocks
    private DeleteProjectService deleteProjectService;

    @Test
    @DisplayName("Should delete project when passing a valid project entity")
    void shouldDeleteProjectWhenPassingAValidProjectEntity() {
        doNothing().when(deleteProjectRepository).delete(isA(Project.class));

        deleteProjectService.delete(new Project());

        verify(deleteProjectRepository, times(1)).delete(any(Project.class));
    }

    @Test
    @DisplayName("Should delete project when passing a valid project id")
    void shouldDeleteProjectWhenPassingAValidProjectId() {
        doNothing().when(deleteProjectRepository).deleteById(isA(Long.class));

        deleteProjectService.delete(1L);

        verify(deleteProjectRepository, times(1)).deleteById(any(Long.class));
    }
}
