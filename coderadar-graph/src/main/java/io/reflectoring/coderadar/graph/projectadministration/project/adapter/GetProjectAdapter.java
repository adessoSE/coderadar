package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.ProjectMapper;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.domain.ProjectWithRoles;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProjectAdapter implements GetProjectPort {
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

  private final ProjectMapper projectMapper = new ProjectMapper();

  @Override
  public Project get(long id) {
    Optional<ProjectEntity> projectEntity = projectRepository.findById(id);
    if (projectEntity.isPresent()) {
      return projectMapper.mapGraphObject(projectEntity.get());
    } else {
      throw new ProjectNotFoundException(id);
    }
  }

  @Override
  public ProjectWithRoles getWithRoles(long projectId, long userId) {
    Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
    if (projectEntity.isPresent()) {
      Set<String> roles =
          new HashSet<>(userRepository.getUserRolesForProjectInTeams(projectId, userId));
      roles.add(userRepository.getUserRoleForProject(projectId, userId));
      roles.removeIf(Objects::isNull);
      return new ProjectWithRoles(
          projectMapper.mapGraphObject(projectEntity.get()),
          roles.stream()
              .map(s -> ProjectRole.valueOf(s.toUpperCase()))
              .collect(Collectors.toList()));
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }

  @Override
  public Project get(String name) {
    Optional<ProjectEntity> projectEntity = projectRepository.findByName(name);
    if (projectEntity.isPresent()) {
      return projectMapper.mapGraphObject(projectEntity.get());
    } else {
      throw new ProjectNotFoundException(name);
    }
  }

  @Override
  public boolean existsByName(String name) {
    return projectRepository.existsByName(name);
  }

  @Override
  public boolean existsById(long projectId) {
    return projectRepository.existsById(projectId);
  }
}
