package io.reflectoring.coderadar.rest;

import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.rest.domain.GetModuleResponse;

import java.util.ArrayList;
import java.util.List;

public class GetModuleResponseMapper {
  private GetModuleResponseMapper() {}

  public static GetModuleResponse mapModule(Module module) {
    return new GetModuleResponse(module.getId(), module.getPath());
  }

  public static List<GetModuleResponse> mapModules(List<Module> modules) {
    List<GetModuleResponse> responses = new ArrayList<>(modules.size());
    for (Module module : modules) {
      responses.add(mapModule(module));
    }
    return responses;
  }
}
