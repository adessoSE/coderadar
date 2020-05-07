package io.reflectoring.coderadar.graph;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.ModuleMapper;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModuleMapperTest {
  private final ModuleMapper moduleMapper = new ModuleMapper();

  @Test
  public void testMapDomainObject() {
    Module testModule = new Module(2L, "testPath");

    ModuleEntity result = moduleMapper.mapDomainObject(testModule);
    Assertions.assertEquals("testPath", result.getPath());
    Assertions.assertNull(result.getId());
  }

  @Test
  public void testMapGraphObject() {
    ModuleEntity testModule = new ModuleEntity().setId(1L).setPath("testPath");

    Module result = moduleMapper.mapGraphObject(testModule);
    Assertions.assertEquals("testPath", result.getPath());
    Assertions.assertEquals(1L, result.getId());
  }
}
