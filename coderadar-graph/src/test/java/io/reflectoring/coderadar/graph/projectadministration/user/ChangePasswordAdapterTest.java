package io.reflectoring.coderadar.graph.projectadministration.user;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.graph.projectadministration.user.repository.UserRepository;
import io.reflectoring.coderadar.graph.projectadministration.user.service.ChangePasswordAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Change password")
public class ChangePasswordAdapterTest {
  private UserRepository userRepository = mock(UserRepository.class);

  private ChangePasswordAdapter changePasswordAdapter;

  @BeforeEach
  void setUp() {
    changePasswordAdapter = new ChangePasswordAdapter(userRepository);
  }
}
