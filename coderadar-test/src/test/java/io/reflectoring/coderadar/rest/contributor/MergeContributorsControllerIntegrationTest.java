package io.reflectoring.coderadar.rest.contributor;

import io.reflectoring.coderadar.contributor.port.driver.MergeContributorsCommand;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultHandler;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MergeContributorsControllerIntegrationTest extends ControllerTestTemplate {
  @Autowired private ContributorRepository contributorRepository;

  @Test
  public void mergesTwoContributors() throws Exception {
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
        .andReturn();

    List<ContributorEntity> contributors = contributorRepository.findAll();
    long firstId = contributors.get(0).getId();
    long secondId = contributors.get(1).getId();
    MergeContributorsCommand command2 =
        new MergeContributorsCommand(Arrays.asList(firstId, secondId), "Test");

    mvc()
        .perform(
            post("/api/contributors/merge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(command2)))
        .andExpect(status().isOk())
        .andDo(documentMergeContributors())
        .andReturn();

    ContributorEntity mergedContributor = contributorRepository.findById(firstId).get();

    Assertions.assertThat(contributorRepository.findById(secondId)).isEmpty();
    Assertions.assertThat(mergedContributor.getDisplayName()).isEqualTo("Test");
    Assertions.assertThat(mergedContributor.getEmails())
        .containsExactlyInAnyOrder("maksim.atanasov@adesso.de", "kilian.krause@adesso.de");
    Assertions.assertThat(mergedContributor.getNames())
        .containsExactlyInAnyOrder("Atanasov", "maximAtanasov", "Krause");
  }

  private ResultHandler documentMergeContributors() {
    ConstrainedFields<MergeContributorsCommand> fields = fields(MergeContributorsCommand.class);
    return document(
        "contributors/merge",
        requestFields(
            fields
                .withPath("contributorIds")
                .description(
                    "The IDs of the contributors to merge. "
                        + "The ID of the first contributor in the list will be the ID of the merged contributor. "
                        + "All other IDs become invalid."),
            fields
                .withPath("displayName")
                .description("The new display name of the merged contributor.")));
  }
}
