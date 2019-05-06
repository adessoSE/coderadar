package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetProjectService implements GetProjectPort {
    private final GetProjectRepository getProjectRepository;

    @Autowired
    public GetProjectService(GetProjectRepository getProjectRepository) {
        this.getProjectRepository = getProjectRepository;
    }

    @Override
    public Optional<Project> get(Long id) {
        return getProjectRepository.findById(id);
    }
}
