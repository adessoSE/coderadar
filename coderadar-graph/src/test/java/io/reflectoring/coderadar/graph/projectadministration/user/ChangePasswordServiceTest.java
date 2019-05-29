package io.reflectoring.coderadar.graph.projectadministration.user;

import io.reflectoring.coderadar.graph.projectadministration.user.repository.ChangePasswordRepository;
import io.reflectoring.coderadar.graph.projectadministration.user.service.ChangePasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.mockito.Mockito.mock;

@DisplayName("Change password")
public class ChangePasswordServiceTest {
  private ChangePasswordRepository changePasswordRepository = mock(ChangePasswordRepository.class);

  @Qualifier("ChangePasswordServiceNeo4j")
  private ChangePasswordService changePasswordService;

  @BeforeEach
  void setUp() {
    changePasswordService = new ChangePasswordService(changePasswordRepository);
  }
}
