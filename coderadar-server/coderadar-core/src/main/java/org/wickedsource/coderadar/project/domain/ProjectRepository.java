package org.wickedsource.coderadar.project.domain;

import org.springframework.data.repository.CrudRepository;
import org.wickedsource.coderadar.projectadministration.domain.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {

  int countById(Long id);

  int countByName(String name);
}
