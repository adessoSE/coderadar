package io.reflectoring.coderadar.rest.contributor;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import java.net.URL;
import java.util.List;
import java.util.Objects;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetContributorControllerIntegrationTest extends ControllerTestTemplate {
  @Autowired private ContributorRepository contributorRepository;
  @Autowired private SessionFactory sessionFactory;

  @Test
  public void getSingleContributor() throws Exception {
    URL testRepoURL = this.getClass().getClassLoader().getResource("test-repository");
    CreateProjectCommand command1 =
        new CreateProjectCommand(
            "test-project",
            "username",
            "password",
            Objects.requireNonNull(testRepoURL).toString(),
            false,
            null,
            null);
    mvc()
        .perform(
            post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command1)))
        .andReturn();

    List<ContributorEntity> contributors = contributorRepository.findAll();
    long contributorId = contributors.get(0).getId();

    MvcResult result =
        mvc()
            .perform(get("/contributors/" + contributorId).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(documentContributor())
            .andReturn();

    Contributor contributor =
        fromJson(result.getResponse().getContentAsString(), Contributor.class);

    Assertions.assertThat(contributor.getId()).isEqualTo(contributorId);
    Assertions.assertThat(contributor.getDisplayName()).isEqualTo("Krause");
    Assertions.assertThat(contributor.getNames()).containsExactly("Krause");
    Assertions.assertThat(contributor.getEmailAddresses())
        .containsExactly("kilian.krause@adesso.de");
  }

  private ResultHandler documentContributor() {
    return document(
        "contributors/get",
        responseFields(
            fieldWithPath("id").description("The id of the contributor."),
            fieldWithPath("displayName").description("The display name of the contributor."),
            fieldWithPath("names")
                .description(
                    "Different names that appear in different commits, but all belong to this contributor."),
            fieldWithPath("emailAddresses")
                .description("Different email addresses of the contributor.")));
  }
}
