package io.reflectoring.coderadar.graph.projectadministration.user;

import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.graph.useradministration.service.ChangePasswordAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.mockito.Mockito.mock;

@DisplayName("Change password")
public class ChangePasswordAdapterTest {
  private UserRepository userRepository = mock(UserRepository.class);

  private ChangePasswordAdapter changePasswordAdapter;

  @BeforeEach
  void setUp() {
    changePasswordAdapter = new ChangePasswordAdapter(userRepository);
  }
}
