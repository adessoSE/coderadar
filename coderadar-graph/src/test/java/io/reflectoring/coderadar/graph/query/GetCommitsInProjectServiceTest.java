package io.reflectoring.coderadar.graph.query;

import io.reflectoring.coderadar.graph.exception.ProjectNotFoundException;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

@ExtendWith(SpringExtension.class)
public class GetCommitsInProjectServiceTest {
    @Mock private GetCommitsInProjectRepository getCommitsInProjectRepository;
    @Mock private GetProjectRepository getProjectRepository;
    @InjectMocks private GetCommitsInProjectService getCommitsInProjectService;

    @Test
    @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
    void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
        Throwable thrown = catchThrowable(() -> getCommitsInProjectService.get(1L));

        assertThat(thrown).isInstanceOf(ProjectNotFoundException.class)
                .hasNoCause()
                .hasMessage("A project with the ID '1' doesn't exists.");
    }


}
