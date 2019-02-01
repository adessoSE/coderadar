package org.wickedsource.coderadar.user.service;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wickedsource.coderadar.core.rest.validation.RefreshTokenNotFoundException;
import org.wickedsource.coderadar.security.domain.RefreshToken;
import org.wickedsource.coderadar.security.domain.RefreshTokenRepository;
import org.wickedsource.coderadar.security.service.TokenService;
import org.wickedsource.coderadar.user.domain.User;
import org.wickedsource.coderadar.user.domain.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
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

	@Test(expected = RefreshTokenNotFoundException.class)
	public void checkNoToken() {
		when(refreshTokenRepository.findByToken(anyString())).thenReturn(null);
		tokenRefreshService.checkUser("jwtToken");
	}

	@Test(expected = UsernameNotFoundException.class)
	public void checkNoUser() {
		RefreshToken refreshToken = new RefreshToken();

		when(refreshTokenRepository.findByToken(anyString())).thenReturn(refreshToken);
		when(tokenService.getUsername(anyString())).thenReturn("radar");
		when(userRepository.findByUsername("radar")).thenReturn(null);

		tokenRefreshService.checkUser("jwtToken");
	}
}
