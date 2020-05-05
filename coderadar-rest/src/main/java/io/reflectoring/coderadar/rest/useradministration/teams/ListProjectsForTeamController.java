package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListProjectsForTeamUseCase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class ListProjectsForTeamController implements AbstractBaseController {
    private final ListProjectsForTeamUseCase listProjectsForTeamUseCase;

    public ListProjectsForTeamController(ListProjectsForTeamUseCase listProjectsForTeamUseCase) {
        this.listProjectsForTeamUseCase = listProjectsForTeamUseCase;
    }
}
