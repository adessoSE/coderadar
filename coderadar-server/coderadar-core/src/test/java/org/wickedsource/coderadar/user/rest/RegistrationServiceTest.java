package org.wickedsource.coderadar.user.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wickedsource.coderadar.core.rest.validation.ResourceNotFoundException;
import org.wickedsource.coderadar.user.domain.User;
import org.wickedsource.coderadar.user.domain.UserRegistrationDataResource;
import org.wickedsource.coderadar.user.domain.UserRepository;
import org.wickedsource.coderadar.user.service.RegistrationService;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
public class RegistrationServiceTest {

  @InjectMocks private RegistrationService registrationService;

  @Mock private UserRepository userRepository;

  @Test
  public void existsUser() throws Exception {
    when(userRepository.findByUsername(anyString())).thenReturn(new User());
    boolean existsUser =
        registrationService.userExists(new UserRegistrationDataResource("username", "password"));
    assertThat(existsUser).isTrue();
  }

  @Test
  public void notExistUser() throws Exception {
    when(userRepository.findByUsername(anyString())).thenReturn(null);
    boolean existsUser =
        registrationService.userExists(new UserRegistrationDataResource("username", "password"));
    assertThat(existsUser).isFalse();
  }

  @Test
  public void getUser() throws Exception {
    User user = new User();
    user.setUsername("user");
    when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));
    User foundUser = registrationService.getUser(1L);
    assertThat(foundUser).isSameAs(user);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void getUserNotFound() throws Exception {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
    registrationService.getUser(1L);
  }
}
