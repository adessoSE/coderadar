package io.reflectoring.coderadar.graph.useradministration.adapter;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.ProjectMapper;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.ProjectWithRoles;
import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.port.driven.ListProjectsForUserPort;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListProjectsForUserAdapter implements ListProjectsForUserPort {

  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper = new ProjectMapper();

  @Override
  public List<ProjectWithRoles> listProjects(long userId) {
    List<Map<String, Object>> projectsWithRoles = projectRepository.findProjectsByUsedId(userId);
    List<ProjectWithRoles> projects = new ArrayList<>(projectsWithRoles.size());
    for (Map<String, Object> project : projectsWithRoles) {
      if (project.get("project") == null) {
        continue;
      }
      projects.add(
          new ProjectWithRoles(
              projectMapper.mapGraphObject((ProjectEntity) project.get("project")),
              Arrays.stream((String[]) project.get("roles"))
                  .map(s -> ProjectRole.valueOf(s.toUpperCase()))
                  .collect(Collectors.toList())));
    }
    return projects;
  }
}
