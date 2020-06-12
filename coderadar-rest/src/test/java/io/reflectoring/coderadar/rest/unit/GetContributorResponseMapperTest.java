package io.reflectoring.coderadar.rest.unit;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.rest.GetContributorResponseMapper;
import io.reflectoring.coderadar.rest.domain.GetContributorResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetContributorResponseMapperTest {

  @Test
  public void testContributorResponseMapper() {
    List<Contributor> contributors = new ArrayList<>();
    contributors.add(
        new Contributor()
            .setId(1L)
            .setDisplayName("testName1")
            .setEmailAddresses(Collections.singleton("testEmail1"))
            .setNames(Collections.singleton("testDisplayName1")));
    contributors.add(
        new Contributor()
            .setId(2L)
            .setDisplayName("testName2")
            .setEmailAddresses(Collections.singleton("testEmail2"))
            .setNames(Collections.singleton("testDisplayName2")));

    List<GetContributorResponse> responses =
        GetContributorResponseMapper.mapContributors(contributors);
    Assertions.assertEquals(2L, responses.size());

    Assertions.assertEquals("testName1", responses.get(0).getDisplayName());
    Assertions.assertEquals(1L, responses.get(0).getId());
    Assertions.assertEquals(Collections.singleton("testDisplayName1"), responses.get(0).getNames());
    Assertions.assertEquals(
        Collections.singleton("testEmail1"), responses.get(0).getEmailAddresses());

    Assertions.assertEquals("testName2", responses.get(1).getDisplayName());
    Assertions.assertEquals(2L, responses.get(1).getId());
    Assertions.assertEquals(Collections.singleton("testDisplayName2"), responses.get(1).getNames());
    Assertions.assertEquals(
        Collections.singleton("testEmail2"), responses.get(1).getEmailAddresses());
  }
}
