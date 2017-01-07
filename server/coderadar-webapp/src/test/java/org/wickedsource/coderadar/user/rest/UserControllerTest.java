package org.wickedsource.coderadar.user.rest;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultHandler;
import org.wickedsource.coderadar.testframework.category.ControllerTest;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;
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
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

@Category(ControllerTest.class)
public class UserControllerTest extends ControllerTestTemplate {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup(EMPTY)
    public void register() throws Exception {
        UserRegistrationDataResource userCredentials = userCredentialsResource().userCredentialsResource();
        mvc().perform(post("/user/register")
                .content(toJsonWithoutLinks(userCredentials))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(containsResource(UserResource.class))
                .andDo(documentCreateProject());

        long count = userRepository.count();
        assertThat(count).isEqualTo(1);
    }

    private ResultHandler documentCreateProject() {
        ConstrainedFields fields = fields(UserRegistrationDataResource.class);
        return document("user/register",

                links(halLinks(),
                        linkWithRel("self").description("Link to the user.")),
                requestFields(
                        fields.withPath("username").description("The name of the user to be registered."),
                        fields.withPath("password").description("The password of the user as plaintext")));
    }

    @Test
    @DatabaseSetup(USERS)
    public void userExists() throws Exception {
        UserRegistrationDataResource userCredentials = userCredentialsResource().userCredentialsResource();
        mvc().perform(post("/user/register")
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
}
