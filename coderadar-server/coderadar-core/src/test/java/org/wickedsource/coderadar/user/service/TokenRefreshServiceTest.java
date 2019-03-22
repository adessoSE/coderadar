package org.wickedsource.coderadar.user.service;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.wickedsource.coderadar.core.rest.validation.RefreshTokenNotFoundException;
import org.wickedsource.coderadar.projectadministration.domain.User;
import org.wickedsource.coderadar.security.domain.RefreshToken;
import org.wickedsource.coderadar.security.domain.RefreshTokenRepository;
import org.wickedsource.coderadar.security.service.TokenService;
import org.wickedsource.coderadar.user.domain.UserRepository;

@ExtendWith(SpringExtension.class)
public class TokenRefreshServiceTest {

  @InjectMocks private TokenRefreshService tokenRefreshService;

  @Mock private RefreshTokenRepository refreshTokenRepository;

  @Mock private TokenService tokenService;

  @Mock private UserRepository userRepository;

  @Test
  public void checkUser() {
    RefreshToken refreshToken = new RefreshToken();
    User user = new User();

    when(refreshTokenRepository.findByToken(anyString())).thenReturn(refreshToken);
    when(tokenService.getUsername(anyString())).thenReturn("radar");
    when(userRepository.findByUsername("radar")).thenReturn(user);

    tokenRefreshService.checkUser("jwtToken");

    verify(tokenService, times(1)).verify(anyString());
  }

  @Test
  public void checkNoToken() {
    when(refreshTokenRepository.findByToken(anyString())).thenReturn(null);
    Assertions.assertThrows(
        RefreshTokenNotFoundException.class, () -> tokenRefreshService.checkUser("jwtToken"));
  }

  @Test
  public void checkNoUser() {
    RefreshToken refreshToken = new RefreshToken();

    when(refreshTokenRepository.findByToken(anyString())).thenReturn(refreshToken);
    when(tokenService.getUsername(anyString())).thenReturn("radar");
    when(userRepository.findByUsername("radar")).thenReturn(null);

    Assertions.assertThrows(
        UsernameNotFoundException.class, () -> tokenRefreshService.checkUser("jwtToken"));
  }
}
