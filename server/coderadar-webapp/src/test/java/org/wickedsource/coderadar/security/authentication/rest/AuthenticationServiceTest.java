package org.wickedsource.coderadar.security.authentication.rest;

import com.auth0.jwt.JWT;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.security.domain.UserCredentialsResource;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationServiceTest extends IntegrationTestTemplate {

    private static final String USER_NAME = "user";
    private static final String ISSUER = "reflectoring.io";

    @Autowired
    private AuthenticationService sut;
    private JWT jwt;

    @Before
    public void setUp() {
        String token = sut.createToken(new UserCredentialsResource("user", "123456"));
        jwt = JWT.decode(token);
    }

    @Test
    public void expiresInFuture() throws Exception {
        assertThat(jwt.getExpiresAt()).describedAs("token expiration date not in the future").isBefore(new Date());
    }

    @Test
    public void issuerIsSet() throws Exception {
        assertThat(jwt.getIssuer()).describedAs("issuer in the token is wrong").isEqualTo(ISSUER);
    }

    @Test
    public void subjectIsUser() throws Exception {
        assertThat(jwt.getSubject()).describedAs("subject of the token is not the user").isEqualTo(USER_NAME);

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
