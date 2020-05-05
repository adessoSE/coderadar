package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListTeamsForProjectUseCase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class ListTeamsForProjectController implements AbstractBaseController {
    private final ListTeamsForProjectUseCase listTeamsForProjectUseCase;

    public ListTeamsForProjectController(ListTeamsForProjectUseCase listTeamsForProjectUseCase) {
        this.listTeamsForProjectUseCase = listTeamsForProjectUseCase;
    }
}
