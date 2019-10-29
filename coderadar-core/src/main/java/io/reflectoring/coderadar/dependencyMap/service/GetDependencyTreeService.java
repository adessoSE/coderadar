package io.reflectoring.coderadar.dependencyMap.service;

import io.reflectoring.coderadar.dependencyMap.domain.Node;
import io.reflectoring.coderadar.dependencyMap.port.driven.GetDependencyTreePort;
import io.reflectoring.coderadar.dependencyMap.port.driver.GetDependencyTreeUseCase;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetDependencyTreeService implements GetDependencyTreeUseCase {

    private final GetDependencyTreePort getDependencyTreePort;
    private final GetProjectPort getProjectPort;

    @Autowired
    public GetDependencyTreeService(GetDependencyTreePort getDependencyTreePort, GetProjectPort getProjectPort) {
        this.getDependencyTreePort = getDependencyTreePort;
        this.getProjectPort = getProjectPort;
    }

    @Override
    public Node getDependencyTree(Long projectId, String commitName) {
        Project project = getProjectPort.get(projectId);
        // TODO is projectRoot correct this way?
        return getDependencyTreePort.getRoot(project.getWorkdirName(), commitName, project.getName());
    }
}
