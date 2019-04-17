package org.wickedsource.coderadar.project.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {

  int countById(Long id);

  int countByName(String name);

  Page<Project> findAll(Pageable pageable);
}
