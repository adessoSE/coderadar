package org.wickedsource.coderadar.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.factories.databases.DbUnitFactory;
import org.wickedsource.coderadar.factories.entities.EntityFactory;
import org.wickedsource.coderadar.projectadministration.domain.User;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

public class UserRepositoryTest extends IntegrationTestTemplate {

  @Autowired private UserRepository repository;

  @Test
  public void testSave() throws Exception {
    User user = repository.save(EntityFactory.user().registeredUser());
    Optional<User> userResult = repository.findById(user.getId());
    assertThat(userResult.isPresent()).isTrue();
  }

  @Test
  @DatabaseSetup(DbUnitFactory.Users.USERS)
  public void testFindOne() throws Exception {
    Optional<User> user = repository.findById(2L);
    assertThat(user.isPresent()).isTrue();
    assertThat(user.get().getUsername()).isEqualTo("radar");
  }

  @Test
  @DatabaseSetup(DbUnitFactory.Users.USERS)
  public void findByUsername() throws Exception {
    User user = repository.findByUsername("radar");
    assertThat(user).isNotNull();
  }

  @Test
  @DatabaseSetup(DbUnitFactory.Users.USERS)
  public void findByUsernameNot() throws Exception {
    User user = repository.findByUsername("andreas");
    assertThat(user).isNull();
  }
}
