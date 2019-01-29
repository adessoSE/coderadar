package org.wickedsource.coderadar.user.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.EMPTY;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Users.USERS;
import static org.wickedsource.coderadar.factories.resources.ResourceFactory.*;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.util.Date;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultHandler;
import org.wickedsource.coderadar.factories.databases.DbUnitFactory;
import org.wickedsource.coderadar.security.TokenType;
import org.wickedsource.coderadar.security.domain.*;
import org.wickedsource.coderadar.security.service.SecretKeyService;
import org.wickedsource.coderadar.testframework.category.ControllerTest;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;
import org.wickedsource.coderadar.user.domain.UserLoginResource;
import org.wickedsource.coderadar.user.domain.UserRegistrationDataResource;
import org.wickedsource.coderadar.user.domain.UserRepository;
import org.wickedsource.coderadar.user.domain.UserResource;

@Category(ControllerTest.class)
public class UserControllerTest extends ControllerTestTemplate {

  @Autowired private UserRepository userRepository;

  @Autowired private RefreshTokenRepository refreshTokenRepository;

  @Autowired private SecretKeyService secretKeyService;

  @Test
  @DatabaseSetup(EMPTY)
  public void register() throws Exception {
    UserRegistrationDataResource userCredentials =
        userCredentialsResource().userCredentialsResource();
    mvc()
        .perform(
            post("/user/registration")
                .content(toJsonWithoutLinks(userCredentials))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(containsResource(UserResource.class))
        .andDo(documentRegistration());

    long count = userRepository.count();
    assertThat(count).isEqualTo(1);
  }

  @Test
  @DatabaseSetup(EMPTY)
  public void registerInvalidPassword() throws Exception {
    UserRegistrationDataResource userCredentials =
        userCredentialsResource().userCredentialsInvalidPasswordResource();
    mvc()
        .perform(
            post("/user/registration")
                .content(toJsonWithoutLinks(userCredentials))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  private ResultHandler documentRegistration() {
    ConstrainedFields fields = fields(UserRegistrationDataResource.class);
    return document(
        "user/registration",
        links(halLinks(), linkWithRel("self").description("Link to the user.")),
        requestFields(
            fields.withPath("username").description("The name of the user to be registered."),
            fields
                .withCustomPath("password")
                .description("The password of the user as plaintext")));
  }

  private ResultHandler documentLogin() {
    ConstrainedFields fields = fields(UserLoginResource.class);
    return document(
        "user/auth",
        links(halLinks(), linkWithRel("self").description("Link to the user.")),
        requestFields(
            fields.withPath("username").description("The name of the user to be logged in."),
            fields.withPath("password").description("The password of the user as plaintext")));
  }

  @Test
  @DatabaseSetup(USERS)
  public void userExists() throws Exception {
    UserRegistrationDataResource userCredentials =
        userCredentialsResource().userCredentialsResource();
    mvc()
        .perform(
            post("/user/registration")
                .content(toJsonWithoutLinks(userCredentials))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict());

    long count = userRepository.count();
    assertThat(count).isEqualTo(2);
  }

  @Test
  @DatabaseSetup(USERS)
  public void getUser() throws Exception {
    mvc()
        .perform(get("/user/1"))
        .andExpect(status().isOk())
        .andExpect(containsResource(UserResource.class))
        .andDo(document("user/get"));
  }

  @Test
  @DatabaseSetup(DbUnitFactory.RefreshTokens.REFRESH_TOKENS)
  public void login() throws Exception {
    UserLoginResource userLoginResource = userLoginResource().userLoginResource();
    mvc()
        .perform(
            post("/user/auth") //
                .content(toJsonWithoutLinks(userLoginResource))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(containsResource(InitializeTokenResource.class))
        .andDo(documentLogin());

    long count = refreshTokenRepository.count();
    assertThat(count).isEqualTo(2);
  }

  @Test
  @DatabaseSetup(DbUnitFactory.RefreshTokens.REFRESH_TOKENS)
  public void refresh() throws Exception {

    // we need to create token here to pass the validation with the current key
    String expiredAccessToken = createExpiredAccessToken();
    String refreshToken = createRefreshToken();

    // save valid refresh token
    RefreshToken refreshTokenEntity = refreshTokenRepository.findOne(100L);
    refreshTokenEntity.setToken(refreshToken);
    refreshTokenRepository.save(refreshTokenEntity);

    RefreshTokenResource refreshTokenResource =
        new RefreshTokenResource(expiredAccessToken, refreshToken);
    mvc()
        .perform(
            post("/user/refresh") //
                .content(toJsonWithoutLinks(refreshTokenResource))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(containsResource(AccessTokenResource.class))
        .andDo(documentRefresh());
  }

  @Test
  @DatabaseSetup(DbUnitFactory.RefreshTokens.REFRESH_TOKENS)
  public void changePassword() throws Exception {

    // we need to create token here to pass the validation with the current key
    String refreshToken = createRefreshToken();
    // save valid refresh token
    RefreshToken refreshTokenEntity = refreshTokenRepository.findOne(100L);
    refreshTokenEntity.setToken(refreshToken);
    refreshTokenRepository.save(refreshTokenEntity);

    PasswordChangeResource passwordChangeResource =
        passwordChangeResource().passwordChangeResource();
    passwordChangeResource.setRefreshToken(refreshToken);

    mvc()
        .perform(
            post("/user/password/change")
                .content(toJsonWithoutLinks(passwordChangeResource))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(containsResource(ChangePasswordResponseResource.class))
        .andDo(documentPasswordChange());
  }

  private ResultHandler documentPasswordChange() {
    ConstrainedFields fields = fields(PasswordChangeResource.class);
    return document(
        "user/password/change",
        links(halLinks(), linkWithRel("self").description("Link to the user.")),
        requestFields(
            fields.withPath("refreshToken").description("the current refresh token of the user"),
            fields
                .withCustomPath("newPassword")
                .description("The password of the user as plaintext")));
  }

  private String createExpiredAccessToken() {
    byte[] secret = secretKeyService.getSecretKey().getEncoded();
    Date expireAt = DateTime.now().minusMinutes(14).toDate();
    Date issuedAt = DateTime.now().minusMinutes(29).toDate();
    return JWT.create() //
        .withExpiresAt(expireAt) //
        .withIssuedAt(issuedAt) //
        .withIssuer("coderadar") //
        .withClaim("userId", 1) //
        .withClaim("username", "radar") //
        .withClaim("type", TokenType.ACCESS.toString())
        .sign(Algorithm.HMAC256(secret));
  }

  private String createRefreshToken() {
    byte[] secret = secretKeyService.getSecretKey().getEncoded();
    Date expireAt = DateTime.now().plusMinutes(3000).toDate();
    Date issuedAt = DateTime.now().minusMinutes(3000).toDate();
    return JWT.create() //
        .withExpiresAt(expireAt) //
        .withIssuedAt(issuedAt) //
        .withIssuer("coderadar") //
        .withClaim("userId", 1) //
        .withClaim("username", "radar") //
        .withClaim("type", TokenType.REFRESH.toString())
        .sign(Algorithm.HMAC256(secret));
  }

  private ResultHandler documentRefresh() {
    ConstrainedFields fields = fields(RefreshTokenResource.class);
    return document(
        "user/refresh",
        requestFields(
            fields.withPath("accessToken").description("The expired access token"),
            fields.withPath("refreshToken").description("The valid refresh token")));
  }
}
