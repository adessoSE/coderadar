package io.reflectoring.coderadar.graph.contributor;

import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.contributor.port.driver.UpdateContributorCommand;
import io.reflectoring.coderadar.graph.contributor.adapter.UpdateContributorAdapter;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdateContributorAdapterTest {
  private UpdateContributorAdapter updateContributorAdapter;

  @Mock private ContributorRepository contributorRepository;

  @BeforeEach
  public void setup() {
    updateContributorAdapter = new UpdateContributorAdapter(contributorRepository);
  }

  @Test
  public void updateDisplayName() {
    ContributorEntity contributor = new ContributorEntity();
    contributor.setId(1L);
    contributor.setDisplayName("Test");

    UpdateContributorCommand command = new UpdateContributorCommand("Tester");

    when(contributorRepository.findById(1L)).thenReturn(Optional.of(contributor));

    updateContributorAdapter.updateContributor(1L, command);

    Assertions.assertEquals("Tester", contributor.getDisplayName());
  }
}
