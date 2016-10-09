package org.wickedsource.coderadar.project.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.rest.validation.ResourceNotFoundException;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

@Component
public class ProjectVerifier {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectVerifier(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Loads the project with the specified ID from the database or throws an
     * exception if it doesn't.
     */
    public Project loadProjectOrThrowException(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("projectId must not be null!");
        }
        Project project = projectRepository.findOne(projectId);
        if (project == null) {
            throw new ResourceNotFoundException();
        } else {
            return project;
        }
    }

    /**
     * Checks if the Project with the given ID exists without loading it from the database.
     */
    public void checkProjectExistsOrThrowException(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("projectId must not be null!");
        }
        int count = projectRepository.countById(projectId);
        if (count == 0) {
            throw new ResourceNotFoundException();
        }
    }
}
