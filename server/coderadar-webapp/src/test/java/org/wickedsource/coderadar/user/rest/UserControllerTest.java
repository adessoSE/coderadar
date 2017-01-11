package org.wickedsource.coderadar.user.rest;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultHandler;
import org.wickedsource.coderadar.factories.databases.DbUnitFactory;
import org.wickedsource.coderadar.security.domain.InitializeTokenResource;
import org.wickedsource.coderadar.security.domain.RefreshTokenRepository;
import org.wickedsource.coderadar.testframework.category.ControllerTest;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;
import org.wickedsource.coderadar.user.domain.UserLoginResource;
import org.wickedsource.coderadar.user.domain.UserRegistrationDataResource;
import org.wickedsource.coderadar.user.domain.UserRepository;
import org.wickedsource.coderadar.user.domain.UserResource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.EMPTY;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Users.USERS;
import static org.wickedsource.coderadar.factories.resources.ResourceFactory.userCredentialsResource;
import static org.wickedsource.coderadar.factories.resources.ResourceFactory.userLoginResource;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

@Category(ControllerTest.class)
public class UserControllerTest extends ControllerTestTemplate {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    @DatabaseSetup(EMPTY)
    public void register() throws Exception {
        UserRegistrationDataResource userCredentials = userCredentialsResource().userCredentialsResource();
        mvc().perform(post("/user/registration")
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
        UserRegistrationDataResource userCredentials = userCredentialsResource().userCredentialsInvalidPasswordResource();
        mvc().perform(post("/user/registration").content(toJsonWithoutLinks(userCredentials)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private ResultHandler documentRegistration() {
        ConstrainedFields fields = fields(UserRegistrationDataResource.class);
        return document("user/registration",

                links(halLinks(),
                        linkWithRel("self").description("Link to the user.")),
                requestFields(
                        fields.withPath("username").description("The name of the user to be registered."),
                        fields.withCustomPath("password").description("The password of the user as plaintext")));
    }

    private ResultHandler documentLogin() {
        ConstrainedFields fields = fields(UserLoginResource.class);
        return document("user/auth",

                links(halLinks(),
                        linkWithRel("self").description("Link to the user.")),
                requestFields(
                        fields.withPath("username").description("The name of the user to be logged in."),
                        fields.withPath("password").description("The password of the user as plaintext")));
    }



    @Test
    @DatabaseSetup(USERS)
    public void userExists() throws Exception {
        UserRegistrationDataResource userCredentials = userCredentialsResource().userCredentialsResource();
        mvc().perform(post("/user/registration")
                              .content(toJsonWithoutLinks(userCredentials))
                              .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        long count = userRepository.count();
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DatabaseSetup(USERS)
    public void getUser() throws Exception {
        mvc().perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(containsResource(UserResource.class))
                .andDo(document("user/get"));
    }

    @Test
    @DatabaseSetup(DbUnitFactory.RefreshTokens.REFRESH_TOKENS)
    public void login() throws Exception {
        UserLoginResource userLoginResource = userLoginResource().userLoginResource();
        mvc().perform(post("/user/auth")//
                              .content(toJsonWithoutLinks(userLoginResource))
                              .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(containsResource(InitializeTokenResource.class))
                .andDo(documentLogin());

        long count = refreshTokenRepository.count();
        assertThat(count).isEqualTo(2);
    }
}
