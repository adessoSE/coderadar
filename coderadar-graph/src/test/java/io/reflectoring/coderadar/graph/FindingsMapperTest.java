package io.reflectoring.coderadar.graph;

import io.reflectoring.coderadar.graph.analyzer.FindingsMapper;
import io.reflectoring.coderadar.plugin.api.Finding;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FindingsMapperTest {
  private final FindingsMapper findingsMapper = new FindingsMapper();

  @Test
  void testMapFindingDomainWithMessage() {
    Finding testFinding = new Finding(1, 2, "message");

    String result = findingsMapper.mapDomainObject(testFinding);
    Assertions.assertEquals("1-2-message", result);
  }

  @Test
  void testMapFindingDomainWithMessageDash() {
    Finding testFinding = new Finding(1, 2, "message-2");

    String result = findingsMapper.mapDomainObject(testFinding);
    Assertions.assertEquals("1-2-message-2", result);
  }

  @Test
  void testMapFindingGraphWithMessage() {
    String testFinding = "1-2-message";

    Finding result = findingsMapper.mapGraphObject(testFinding);
    Assertions.assertEquals(1, result.getLineStart());
    Assertions.assertEquals(2, result.getLineEnd());
    Assertions.assertEquals("message", result.getMessage());
  }

  @Test
  void testMapFindingGraphWithMessageDash() {
    String testFinding = "1-2-message-2";

    Finding result = findingsMapper.mapGraphObject(testFinding);
    Assertions.assertEquals(1, result.getLineStart());
    Assertions.assertEquals(2, result.getLineEnd());
    Assertions.assertEquals("message-2", result.getMessage());
  }
}
