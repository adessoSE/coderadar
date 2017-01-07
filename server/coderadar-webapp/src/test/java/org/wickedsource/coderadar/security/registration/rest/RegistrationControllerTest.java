package org.wickedsource.coderadar.security.registration.rest;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultHandler;
import org.wickedsource.coderadar.security.domain.UserRegistrationDataResource;
import org.wickedsource.coderadar.security.domain.UserRepository;
import org.wickedsource.coderadar.security.domain.UserResource;
import org.wickedsource.coderadar.testframework.category.ControllerTest;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.EMPTY;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Users.USERS;
import static org.wickedsource.coderadar.factories.resources.ResourceFactory.userCredentialsResource;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

@Category(ControllerTest.class)
public class RegistrationControllerTest extends ControllerTestTemplate {

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

                requestFields(
                        fields.withPath("username").description("The name of the user to be registered."),
                        fields.withPath("password").description("The password of the user as plaintext")));
    }

    @Test
    @DatabaseSetup(USERS)
    public void usrExists() throws Exception {
        UserRegistrationDataResource userCredentials = userCredentialsResource().userCredentialsResource();
        mvc().perform(post("/user/register")
                .content(toJsonWithoutLinks(userCredentials))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        long count = userRepository.count();
        assertThat(count).isEqualTo(2);
    }

}
