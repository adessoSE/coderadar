package io.reflectoring.coderadar.useradministration.service.permissions;

import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.port.driver.permissions.SetUserRoleForProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class SetUserRoleForProjectService implements SetUserRoleForProjectUseCase {
    @Override
    public void setRole(long projectId, long userId, ProjectRole role) {

    }
}
