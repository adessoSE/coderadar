package io.reflectoring.coderadar.rest.unit;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.rest.GetCommitResponseMapper;
import io.reflectoring.coderadar.rest.domain.GetCommitResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GetCommitResponseMapperTest {

  @Test
  void testCommitResponseMapper() {
    List<Commit> commits = new ArrayList<>();
    commits.add(
        new Commit()
            .setHash("testHash1")
            .setAnalyzed(true)
            .setAuthor("testAuthor1")
            .setAuthorEmail("testEmail1")
            .setComment("testComment1")
            .setId(1L)
            .setTimestamp(0L));
    commits.add(
        new Commit()
            .setHash("testHash2")
            .setAnalyzed(false)
            .setAuthor("testAuthor2")
            .setAuthorEmail("testEmail2")
            .setComment("testComment2")
            .setId(2L)
            .setTimestamp(1L));

    List<GetCommitResponse> responses = GetCommitResponseMapper.mapCommits(commits);
    Assertions.assertEquals(2L, responses.size());
    Assertions.assertEquals("testHash1", responses.get(0).getHash());
    Assertions.assertTrue(responses.get(0).isAnalyzed());
    Assertions.assertEquals("testAuthor1", responses.get(0).getAuthor());
    Assertions.assertEquals("testEmail1", responses.get(0).getAuthorEmail());
    Assertions.assertEquals("testComment1", responses.get(0).getComment());
    Assertions.assertEquals(0L, responses.get(0).getTimestamp());

    Assertions.assertEquals("testHash2", responses.get(1).getHash());
    Assertions.assertFalse(responses.get(1).isAnalyzed());
    Assertions.assertEquals("testAuthor2", responses.get(1).getAuthor());
    Assertions.assertEquals("testEmail2", responses.get(1).getAuthorEmail());
    Assertions.assertEquals("testComment2", responses.get(1).getComment());
    Assertions.assertEquals(1L, responses.get(1).getTimestamp());
  }
}
