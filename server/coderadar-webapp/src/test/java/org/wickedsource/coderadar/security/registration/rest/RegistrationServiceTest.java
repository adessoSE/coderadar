package org.wickedsource.coderadar.security.registration.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wickedsource.coderadar.security.domain.User;
import org.wickedsource.coderadar.security.domain.UserRegistrationDataResource;
import org.wickedsource.coderadar.security.domain.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class RegistrationServiceTest {

    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void existsUser() throws Exception {
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        boolean existsUser = registrationService.userExists(new UserRegistrationDataResource("username", "password"));
        assertThat(existsUser).isTrue();
    }

    @Test
    public void notExistUser() throws Exception {
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        boolean existsUser = registrationService.userExists(new UserRegistrationDataResource("username", "password"));
        assertThat(existsUser).isFalse();
    }
}
