package org.wickedsource.coderadar;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

@Profile(MockedRepositoriesConfiguration.PROFILE)
@Configuration
public class MockedRepositoriesConfiguration {

    public final static String PROFILE = "mockedRepositories";

    @Bean
    @Primary
    public ProjectRepository projectRepository() {
        return Mockito.mock(ProjectRepository.class);
    }
}
