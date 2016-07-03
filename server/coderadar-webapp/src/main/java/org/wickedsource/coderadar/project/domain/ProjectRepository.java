package org.wickedsource.coderadar.project.domain;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    int countById(Long id);

    int deleteById(Long id);

}
