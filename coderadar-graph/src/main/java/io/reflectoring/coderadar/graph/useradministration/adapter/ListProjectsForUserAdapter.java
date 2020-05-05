package io.reflectoring.coderadar.graph.useradministration.adapter;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.useradministration.port.driven.ListProjectsForUserPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListProjectsForUserAdapter implements ListProjectsForUserPort {
  @Override
  public List<Project> listProjects(long userId) {
    return null;
  }
}
