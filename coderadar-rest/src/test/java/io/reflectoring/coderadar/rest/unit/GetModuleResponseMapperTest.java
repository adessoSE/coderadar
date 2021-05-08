package io.reflectoring.coderadar.rest.unit;

import io.reflectoring.coderadar.domain.Module;
import io.reflectoring.coderadar.rest.GetModuleResponseMapper;
import io.reflectoring.coderadar.rest.domain.GetModuleResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GetModuleResponseMapperTest {

  @Test
  void testModuleResponseMapper() {
    List<Module> modules = new ArrayList<>();
    modules.add(new Module(1L, "testModule1"));
    modules.add(new Module(2L, "testModule2"));

    List<GetModuleResponse> responses = GetModuleResponseMapper.mapModules(modules);
    Assertions.assertEquals(2L, responses.size());

    Assertions.assertEquals("testModule1", responses.get(0).getPath());
    Assertions.assertEquals(1L, responses.get(0).getId());
    Assertions.assertEquals("testModule2", responses.get(1).getPath());
    Assertions.assertEquals(2L, responses.get(1).getId());
  }

  @Test
  void testModuleResponseSingleMapper() {
    Module module = new Module(1L, "testPath1");
    GetModuleResponse response = GetModuleResponseMapper.mapModule(module);
    Assertions.assertEquals("testPath1", response.getPath());
    Assertions.assertEquals(1L, response.getId());
  }
}
