package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.graph.projectadministration.project.ProjectMapper;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.CreateProjectPort;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Service
public class CreateProjectAdapter implements CreateProjectPort {
  private final ProjectRepository projectRepository;

  private final ProjectMapper projectMapper = new ProjectMapper();

  @Autowired
  public CreateProjectAdapter(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public Long createProject(Project project) {
    return projectRepository.save(projectMapper.mapDomainObject(project)).getId();
  }

  @Autowired private SessionFactory sessionFactory;

  @PostConstruct
  public void createIndexesAndConstraints() {
    Session session = sessionFactory.openSession();
    session.query("CREATE INDEX ON :ProjectEntity(id)", Collections.emptyMap());
    session.query("CREATE INDEX ON :ModuleEntity(id)", Collections.emptyMap());
  }
}
