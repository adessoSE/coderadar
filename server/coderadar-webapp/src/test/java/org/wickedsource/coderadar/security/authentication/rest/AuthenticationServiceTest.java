package org.wickedsource.coderadar.security.authentication.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.auth0.jwt.JWT;

public class AuthenticationServiceTest {

    private static final String USER_NAME = "user";
    private AuthenticationService sut = new AuthenticationService();
    private JWT jwt;

    @Before
    public void setUp() {
        String token = sut.createToken(new UserLoginResource("user", "123456"));
        jwt = JWT.decode(token);
    }

    @Test
    public void expiresInFuture() throws Exception {
        assertThat(jwt.getExpiresAt()).describedAs("token expiration date not in the future").isBefore(new Date());
    }

    @Test
    public void issuerIsUsername() throws Exception {
        assertThat(jwt.getIssuer()).describedAs("issuer in the token is not the user").isEqualTo(USER_NAME);
    }

    @Test
    public void algorithmIsHMAC256() throws Exception {
        assertThat(jwt.getAlgorithm()).isEqualTo("HS256");
    }

    @Test
    public void jwtIdIsNotNull() throws Exception {
        assertThat(jwt.getId()).isNotNull();

    }
}
