package org.wickedsource.coderadar.user.domain;

import org.springframework.data.repository.CrudRepository;
import org.wickedsource.coderadar.projectadministration.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
}
