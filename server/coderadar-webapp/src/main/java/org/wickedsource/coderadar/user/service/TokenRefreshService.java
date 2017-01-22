package org.wickedsource.coderadar.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.core.rest.validation.RefreshTokenNotFoundException;
import org.wickedsource.coderadar.security.domain.RefreshToken;
import org.wickedsource.coderadar.security.domain.RefreshTokenRepository;
import org.wickedsource.coderadar.security.service.TokenService;
import org.wickedsource.coderadar.user.domain.User;
import org.wickedsource.coderadar.user.domain.UserRepository;

/**
 * Service for refreshing a access token, if access token is expired.
 */
@Service
public class TokenRefreshService {

    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final TokenService tokenService;

    @Autowired
    public TokenRefreshService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenService = tokenService;
    }

    /**
     * Checks the validity of refresh token and validity of the user, who sent the token.
     *
     * @param refreshToken the refresh token of the user to check
     */
    void checkUser(String refreshToken) {
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken);
        if (refreshTokenEntity == null) {
            throw new RefreshTokenNotFoundException();
        }
        String username = tokenService.getUsername(refreshToken);
        User user = userRepository.findByUsername(username);
        // TODO check user
        if (user == null) {
            throw new UsernameNotFoundException(String.format("The user with username %s was not found", username));
        }
        tokenService.verify(refreshToken);
    }

    /**
     * Creates new access token, if the refresh token and the user having token are valid.
     *
     * @param refreshToken the refresh token to be cheked
     * @return access token
     */
    public String createAccessToken(String refreshToken) {
        checkUser(refreshToken);
        String username = tokenService.getUsername(refreshToken);
        User user = userRepository.findByUsername(username);
        return tokenService.generateAccessToken(user.getId(), user.getUsername());
    }
}
