package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.port.driver.teams.AddUsersToTeamUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class AddUsersToTeamController implements AbstractBaseController {
    private final AddUsersToTeamUseCase addUsersToTeamUseCase;

    public AddUsersToTeamController(AddUsersToTeamUseCase addUsersToTeamUseCase) {
        this.addUsersToTeamUseCase = addUsersToTeamUseCase;
    }

    @PostMapping(path = "/teams/{teamId}/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> addUsersToTeam(@PathVariable long teamId, @RequestBody List<Long> userIds) {
        addUsersToTeamUseCase.addUsersToTeam(teamId, userIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
