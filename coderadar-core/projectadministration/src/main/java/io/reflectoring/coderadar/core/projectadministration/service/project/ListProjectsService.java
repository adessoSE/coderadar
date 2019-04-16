package io.reflectoring.coderadar.core.projectadministration.service.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.ListProjectsPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.ListProjectsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListProjectsService implements ListProjectsUseCase {

    private final ListProjectsPort listProjectsPort;

    @Autowired
    public ListProjectsService(ListProjectsPort listProjectsPort) {
        this.listProjectsPort = listProjectsPort;
    }

    @Override
    public List<Project> listProjects() {
        return listProjectsPort.getProjects();
    }
}
