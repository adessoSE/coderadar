package io.reflectoring.coderadar.graph.projectadministration.user;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.graph.projectadministration.user.repository.ChangePasswordRepository;
import io.reflectoring.coderadar.graph.projectadministration.user.service.ChangePasswordAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Change password")
public class ChangePasswordAdapterTest {
  private ChangePasswordRepository changePasswordRepository = mock(ChangePasswordRepository.class);

  private ChangePasswordAdapter changePasswordAdapter;

  @BeforeEach
  void setUp() {
    changePasswordAdapter = new ChangePasswordAdapter(changePasswordRepository);
  }
}
