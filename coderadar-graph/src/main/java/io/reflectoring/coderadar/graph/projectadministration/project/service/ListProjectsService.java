package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.ListProjectsPort;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ListProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListProjectsService implements ListProjectsPort {
    private final ListProjectsRepository listProjectsRepository;

    @Autowired
    public ListProjectsService(ListProjectsRepository listProjectsRepository) {
        this.listProjectsRepository = listProjectsRepository;
    }

    @Override
    public Iterable<Project> getProjects() {
        return listProjectsRepository.findAll();
    }
}
