package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamForProjectUseCase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class CreateTeamForProjectController implements AbstractBaseController {
    private final CreateTeamForProjectUseCase createTeamForProjectUseCase;

    public CreateTeamForProjectController(CreateTeamForProjectUseCase createTeamForProjectUseCase) {
        this.createTeamForProjectUseCase = createTeamForProjectUseCase;
    }
}
