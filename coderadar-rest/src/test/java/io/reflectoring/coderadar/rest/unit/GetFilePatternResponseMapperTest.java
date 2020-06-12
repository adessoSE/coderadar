package io.reflectoring.coderadar.rest.unit;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.rest.GetFilePatternResponseMapper;
import io.reflectoring.coderadar.rest.domain.GetFilePatternResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GetFilePatternResponseMapperTest {

  @Test
  public void testFilePatternResponseMapper() {
    List<FilePattern> filePatterns = new ArrayList<>();
    filePatterns.add(new FilePattern(1L, "testPattern1/**.java", InclusionType.EXCLUDE));
    filePatterns.add(new FilePattern(2L, "testPattern2/**.java", InclusionType.INCLUDE));

    List<GetFilePatternResponse> responses =
        GetFilePatternResponseMapper.mapFilePatterns(filePatterns);
    Assertions.assertEquals(2L, responses.size());

    Assertions.assertEquals("testPattern1/**.java", responses.get(0).getPattern());
    Assertions.assertEquals(1L, responses.get(0).getId());
    Assertions.assertEquals(InclusionType.EXCLUDE, responses.get(0).getInclusionType());

    Assertions.assertEquals("testPattern2/**.java", responses.get(1).getPattern());
    Assertions.assertEquals(2L, responses.get(1).getId());
    Assertions.assertEquals(InclusionType.INCLUDE, responses.get(1).getInclusionType());
  }

  @Test
  public void testFilePatternResponseSingleMapper() {
    FilePattern filePattern = new FilePattern(1L, "testPattern1/**.java", InclusionType.EXCLUDE);
    GetFilePatternResponse response = GetFilePatternResponseMapper.mapFilePattern(filePattern);
    Assertions.assertEquals("testPattern1/**.java", response.getPattern());
    Assertions.assertEquals(1L, response.getId());
    Assertions.assertEquals(InclusionType.EXCLUDE, response.getInclusionType());
  }
}
