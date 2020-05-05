package io.reflectoring.coderadar.useradministration.service.permissions;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.SetUserRoleForProjectPort;
import io.reflectoring.coderadar.useradministration.port.driver.permissions.SetUserRoleForProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class SetUserRoleForProjectService implements SetUserRoleForProjectUseCase {

    private final GetProjectPort getProjectPort;
    private final GetUserPort getUserPort;
    private final SetUserRoleForProjectPort setUserRoleForProjectPort;

    public SetUserRoleForProjectService(GetProjectPort getProjectPort, GetUserPort getUserPort, SetUserRoleForProjectPort setUserRoleForProjectPort) {
        this.getProjectPort = getProjectPort;
        this.getUserPort = getUserPort;
        this.setUserRoleForProjectPort = setUserRoleForProjectPort;
    }

    @Override
    public void setRole(long projectId, long userId, ProjectRole role) {
        if(!getProjectPort.existsById(projectId)){
            throw new ProjectNotFoundException(projectId);
        }
        if(!getUserPort.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        setUserRoleForProjectPort.setRole(projectId, userId, role);
    }
}
