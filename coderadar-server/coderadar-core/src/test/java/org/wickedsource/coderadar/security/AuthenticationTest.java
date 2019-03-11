package org.wickedsource.coderadar.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Users.USERS;
import static org.wickedsource.coderadar.factories.resources.ResourceFactory.userLoginResource;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import javax.servlet.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.wickedsource.coderadar.factories.databases.DbUnitFactory;
import org.wickedsource.coderadar.security.domain.InitializeTokenResource;
import org.wickedsource.coderadar.security.domain.RefreshTokenRepository;
import org.wickedsource.coderadar.security.service.TokenService;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;
import org.wickedsource.coderadar.user.domain.UserLoginResource;

/**
 * This test class enables servlet filters for authentication of request. The authentication is
 * disabled in other integration tests.
 */
public class AuthenticationTest extends IntegrationTestTemplate {

  private MockMvc mvc;

  @Autowired private WebApplicationContext applicationContext;

  @Autowired private Filter springSecurityFilterChain;

  @Autowired private RefreshTokenRepository refreshTokenRepository;

  @Autowired private TokenService tokenService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);

    mvc =
        MockMvcBuilders.webAppContextSetup(applicationContext)
            .addFilters(springSecurityFilterChain)
            .build();
  }

  @Test
  @DatabaseSetup(USERS)
  public void getUser() throws Exception {
    String token = tokenService.generateAccessToken(1L, "radar");
    mvc.perform(get("/user/1").header(AuthenticationTokenFilter.TOKEN_HEADER, token))
        .andExpect(status().isOk());
  }

  @Test
  @DatabaseSetup(USERS)
  public void getUserForbidden() throws Exception {
    String token = tokenService.generateAccessToken(1L, "radar");
    mvc.perform(get("/user/1").header(AuthenticationTokenFilter.TOKEN_HEADER, token + "invalid"))
        .andExpect(status().isForbidden());
  }

  @Test
  @DatabaseSetup(DbUnitFactory.RefreshTokens.REFRESH_TOKENS)
  public void login() throws Exception {
    UserLoginResource userLoginResource = userLoginResource().userLoginResource();
    mvc.perform(
            post("/user/auth") //
                .content(toJsonWithoutLinks(userLoginResource))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(containsResource(InitializeTokenResource.class));

    long count = refreshTokenRepository.count();
    assertThat(count).isEqualTo(2);
  }
}
