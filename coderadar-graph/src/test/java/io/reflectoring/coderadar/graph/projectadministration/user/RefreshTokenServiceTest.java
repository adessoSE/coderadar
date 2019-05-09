package io.reflectoring.coderadar.graph.projectadministration.user;

import io.reflectoring.coderadar.graph.projectadministration.user.repository.RefreshTokenRepository;
import io.reflectoring.coderadar.graph.projectadministration.user.service.RefreshTokenService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Refresh token")
public class RefreshTokenServiceTest {
    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @Test
    @DisplayName("Should return new access token when passing refresh token")
    void shouldReturnNewAccessTokenWhenPassingRefreshToken() {
        String returnedAccessToken = refreshTokenService.createAccessToken("1A");
        Assertions.assertThat(returnedAccessToken).isNotNull();
    }
}
