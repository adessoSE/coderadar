package io.reflectoring.coderadar.useradministration.service.permissions;

import io.reflectoring.coderadar.useradministration.port.driver.permissions.DeleteUserRoleForProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserRoleForProjectService implements DeleteUserRoleForProjectUseCase {
    @Override
    public void deleteRole(long projectId, long userId) {

    }
}
