package io.reflectoring.coderadar.graph.contributor;

import io.reflectoring.coderadar.graph.contributor.adapter.MergeContributorsAdapter;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MergeContributorsAdapterTest {
  private MergeContributorsAdapter mergeContributorsAdapter;

  @Mock private ContributorRepository contributorRepository;

  @BeforeEach
  public void setup() {
    mergeContributorsAdapter = new MergeContributorsAdapter(contributorRepository);
  }

  @Test
  public void mergeTwoContributorsSuccessfully() {
    ContributorEntity contributor1 = new ContributorEntity();
    contributor1.setId(1L);
    contributor1.setDisplayName("Max");
    contributor1.setNames(new HashSet<>(Collections.singletonList("Max")));
    contributor1.setEmails(new HashSet<>(Collections.singletonList("max.mustermann@abc.de")));

    ContributorEntity contributor2 = new ContributorEntity();
    contributor2.setId(2L);
    contributor2.setDisplayName("Max Mustermann");
    contributor2.setNames(new HashSet<>(Arrays.asList("Max Mustermann", "Mustermann")));
    contributor2.setEmails(new HashSet<>(Collections.singletonList("max.mustermann@def.de")));

    when(contributorRepository.findAllByIdsWithProjects(anyList()))
        .thenReturn(Arrays.asList(contributor1, contributor2));
    when(contributorRepository.save(contributor1, 1)).thenReturn(contributor1);

    mergeContributorsAdapter.mergeContributors(Arrays.asList(1L, 2L), "Max Mustermann");

    Assertions.assertThat(contributor1.getId()).isEqualTo(1L);
    Assertions.assertThat(contributor1.getDisplayName()).isEqualTo("Max Mustermann");
    Assertions.assertThat(contributor1.getNames())
        .containsExactlyInAnyOrder("Max", "Max Mustermann", "Mustermann");
    Assertions.assertThat(contributor1.getEmails())
        .containsExactlyInAnyOrder("max.mustermann@abc.de", "max.mustermann@def.de");
  }
}
