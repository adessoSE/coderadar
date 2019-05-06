package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.DeleteProjectPort;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.DeleteProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteProjectService implements DeleteProjectPort {
    private final DeleteProjectRepository deleteProjectRepository;

    @Autowired
    public DeleteProjectService(DeleteProjectRepository deleteProjectRepository) {
        this.deleteProjectRepository = deleteProjectRepository;
    }

    @Override
    public void delete(Long id) {
        deleteProjectRepository.deleteById(id);
    }

    @Override
    public void delete(Project project) {
        deleteProjectRepository.delete(project);
    }
}
