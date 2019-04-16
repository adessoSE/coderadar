package io.reflectoring.coderadar.core.projectadministration.port.driver.project;

public interface CreateProjectUseCase {
    Long createProject(CreateProjectCommand command);
}
