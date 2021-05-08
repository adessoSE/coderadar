package io.reflectoring.coderadar.graph;

import io.reflectoring.coderadar.domain.Contributor;
import io.reflectoring.coderadar.graph.contributor.ContributorMapper;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ContributorMapperTest {
  private final ContributorMapper contributorMapper = new ContributorMapper();

  @Test
  void testMapDomainObject() {
    Contributor testContributor =
        new Contributor()
            .setDisplayName("testDisplayName")
            .setEmailAddresses(Collections.singleton("testEmail"))
            .setId(1L)
            .setNames(Collections.singleton("testName"));

    ContributorEntity result = contributorMapper.mapDomainObject(testContributor);
    Assertions.assertEquals("testDisplayName", result.getDisplayName());
    Assertions.assertEquals(Collections.singleton("testEmail"), result.getEmails());
    Assertions.assertEquals(Collections.singleton("testName"), result.getNames());
    Assertions.assertEquals(1L, result.getId()); // The only mapper with this edge case
  }

  @Test
  void testMapGraphObject() {
    ContributorEntity testContributor =
        new ContributorEntity()
            .setDisplayName("testDisplayName")
            .setEmails(Collections.singleton("testEmail"))
            .setId(1L)
            .setNames(Collections.singleton("testName"));

    Contributor result = contributorMapper.mapGraphObject(testContributor);
    Assertions.assertEquals("testDisplayName", result.getDisplayName());
    Assertions.assertEquals(Collections.singleton("testEmail"), result.getEmailAddresses());
    Assertions.assertEquals(Collections.singleton("testName"), result.getNames());
    Assertions.assertEquals(1L, result.getId());
  }
}
