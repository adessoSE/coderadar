package io.reflectoring.coderadar.graph;

import io.reflectoring.coderadar.domain.FilePattern;
import io.reflectoring.coderadar.domain.InclusionType;
import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.FilePatternMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FilePatternMapperTest {
  private final FilePatternMapper filePatternMapper = new FilePatternMapper();

  @Test
  void testMapDomainObject() {
    FilePattern testPattern =
        new FilePattern()
            .setId(1L)
            .setPattern("testPattern")
            .setInclusionType(InclusionType.EXCLUDE);

    FilePatternEntity result = filePatternMapper.mapDomainObject(testPattern);
    Assertions.assertEquals("testPattern", result.getPattern());
    Assertions.assertNull(result.getId());
    Assertions.assertEquals(InclusionType.EXCLUDE, result.getInclusionType());
  }

  @Test
  void testMapGraphObject() {
    FilePatternEntity testPattern =
        new FilePatternEntity()
            .setId(1L)
            .setPattern("testPattern")
            .setInclusionType(InclusionType.EXCLUDE);

    FilePattern result = filePatternMapper.mapGraphObject(testPattern);
    Assertions.assertEquals("testPattern", result.getPattern());
    Assertions.assertEquals(1L, result.getId());
    Assertions.assertEquals(InclusionType.EXCLUDE, result.getInclusionType());
  }
}
