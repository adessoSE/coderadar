package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.GetProjectResponseMapper;
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListProjectsForTeamUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.reflectoring.coderadar.rest.GetProjectResponseMapper.mapProjects;

@RestController
@Transactional
public class ListProjectsForTeamController implements AbstractBaseController {
    private final ListProjectsForTeamUseCase listProjectsForTeamUseCase;

    public ListProjectsForTeamController(ListProjectsForTeamUseCase listProjectsForTeamUseCase) {
        this.listProjectsForTeamUseCase = listProjectsForTeamUseCase;
    }

    @GetMapping(path = "/teams/{teamId}/projects")
    public ResponseEntity<List<GetProjectResponse>> listProjectsForTeam(@PathVariable long teamId) {
        List<Project> projects = listProjectsForTeamUseCase.listProjects(teamId);
        return new ResponseEntity<>(mapProjects(projects), HttpStatus.OK);
    }
}
