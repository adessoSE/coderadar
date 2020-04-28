package io.reflectoring.coderadar.rest.contributor;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.reflectoring.coderadar.contributor.port.driver.UpdateContributorCommand;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import java.net.URL;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultHandler;

public class UpdateContributorControllerIntegrationTest extends ControllerTestTemplate {
  @Autowired private ContributorRepository contributorRepository;

  @Test
  public void updateContributor() throws Exception {
    URL testRepoURL = this.getClass().getClassLoader().getResource("test-repository");
    CreateProjectCommand command1 =
        new CreateProjectCommand(
            "test-project",
            "username",
            "password",
            Objects.requireNonNull(testRepoURL).toString(),
            false,
            null);

    mvc()
        .perform(
            post("/api/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command1)))
        .andExpect(status().isCreated())
        .andReturn();

    long contributorId = contributorRepository.findAll().get(0).getId();
    UpdateContributorCommand command = new UpdateContributorCommand("New DisplayName");
    mvc()
        .perform(
            post("/api/contributors/" + contributorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(command)))
        .andExpect(status().isOk())
        .andDo(documentUpdateContributor())
        .andReturn();

    ContributorEntity contributor = contributorRepository.findById(contributorId).get();

    Assertions.assertThat(contributor.getDisplayName()).isEqualTo("New DisplayName");
  }

  private ResultHandler documentUpdateContributor() {
    ConstrainedFields<UpdateContributorCommand> fields = fields(UpdateContributorCommand.class);
    return document(
        "contributors/update",
        requestFields(
            fields
                .withPath("displayName")
                .description("The new display name of the contributor.")));
  }
}
