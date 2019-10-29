package io.reflectoring.coderadar.dependencyMap.service;

import io.reflectoring.coderadar.dependencyMap.domain.CompareNode;
import io.reflectoring.coderadar.dependencyMap.port.driven.GetCompareTreePort;
import io.reflectoring.coderadar.dependencyMap.port.driver.GetCompareTreeUseCase;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCompareTreeService implements GetCompareTreeUseCase {
    private final GetCompareTreePort getCompareTreePort;
    private final GetProjectPort getProjectPort;

    @Autowired
    public GetCompareTreeService(GetCompareTreePort getCompareTreePort, GetProjectPort getProjectPort) {
        this.getCompareTreePort = getCompareTreePort;
        this.getProjectPort = getProjectPort;
    }

    @Override
    public CompareNode getDependencyTree(Long projectId, String commitName, String secondCommit) {
        Project project = getProjectPort.get(projectId);
        // TODO is projectRoot correct this way?
        return getCompareTreePort.getRoot(project.getWorkdirName(), commitName, project.getName(), secondCommit);
    }
}
