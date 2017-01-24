package org.wickedsource.coderadar.user.domain;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.factories.databases.DbUnitFactory;
import org.wickedsource.coderadar.factories.entities.EntityFactory;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest extends IntegrationTestTemplate {

  @Autowired private UserRepository repository;

  @Test
  public void testSave() throws Exception {
    User user = repository.save(EntityFactory.user().registeredUser());
    user = repository.findOne(user.getId());
    assertThat(user).isNotNull();
  }

  @Test
  @DatabaseSetup(DbUnitFactory.Users.USERS)
  public void testFindOne() throws Exception {
    User user = repository.findOne(2L);
    assertThat(user.getUsername()).isEqualTo("radar");
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
