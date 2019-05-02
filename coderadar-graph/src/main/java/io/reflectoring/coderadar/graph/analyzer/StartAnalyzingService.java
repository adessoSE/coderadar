package io.reflectoring.coderadar.graph.analyzer;

import io.reflectoring.coderadar.core.analyzer.domain.AnalyzingJob;
import io.reflectoring.coderadar.core.analyzer.port.driven.StartAnalyzingPort;
import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.exception.ProjectNotFoundException;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StartAnalyzingService implements StartAnalyzingPort {
    private final GetProjectRepository getProjectRepository;
    private final StartAnalyzingRepository startAnalyzingRepository;

    @Autowired
    public StartAnalyzingService(GetProjectRepository getProjectRepository, StartAnalyzingRepository startAnalyzingRepository) {
        this.getProjectRepository = getProjectRepository;
        this.startAnalyzingRepository = startAnalyzingRepository;
    }

    @Override
    public Long start(Long projectId, AnalyzingJob analyzingJob) {
        Optional<Project> persistedProject = getProjectRepository.findById(projectId);

        if (persistedProject.isPresent()) {
            return startAnalyzingRepository.save(analyzingJob).getId();
        } else {
            throw new ProjectNotFoundException("Can't analyze a non-existing project.");
        }
    }
}
