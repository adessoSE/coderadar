package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.port.driver.teams.DeleteTeamFromProjectUseCase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class DeleteTeamFromProjectController implements AbstractBaseController {
    private final DeleteTeamFromProjectUseCase deleteTeamFromProjectUseCase;

    public DeleteTeamFromProjectController(DeleteTeamFromProjectUseCase deleteTeamFromProjectUseCase) {
        this.deleteTeamFromProjectUseCase = deleteTeamFromProjectUseCase;
    }
}
