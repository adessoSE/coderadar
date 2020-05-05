package io.reflectoring.coderadar.rest.useradministration.permissions;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.port.driver.permissions.SetUserRoleForProjectUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class SetUserRoleForProjectController implements AbstractBaseController {
    private final SetUserRoleForProjectUseCase setUserRoleForProjectUseCase;

    public SetUserRoleForProjectController(SetUserRoleForProjectUseCase setUserRoleForProjectUseCase) {
        this.setUserRoleForProjectUseCase = setUserRoleForProjectUseCase;
    }

    @PostMapping(path = "/projects/{projectId}/users/{userId}")
    public ResponseEntity<HttpStatus> setUserRoleForProject(@PathVariable long projectId, @PathVariable long userId) {
        setUserRoleForProjectUseCase.setRole(projectId, userId, ProjectRole.ADMIN); // TODO: change last parameter
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
