package io.reflectoring.coderadar.rest;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.rest.domain.GetAnalyzerConfigurationResponse;
import java.util.ArrayList;
import java.util.List;

public class GetAnalyzerConfigurationResponseMapper {
  private GetAnalyzerConfigurationResponseMapper() {}

  public static GetAnalyzerConfigurationResponse mapAnalyzerConfiguration(
      AnalyzerConfiguration ac) {
    return new GetAnalyzerConfigurationResponse(ac.getId(), ac.getAnalyzerName(), ac.isEnabled());
  }

  public static List<GetAnalyzerConfigurationResponse> mapAnalyzerConfigurations(
      List<AnalyzerConfiguration> analyzerConfigurations) {
    List<GetAnalyzerConfigurationResponse> result = new ArrayList<>(analyzerConfigurations.size());
    for (AnalyzerConfiguration ac : analyzerConfigurations) {
      result.add(mapAnalyzerConfiguration(ac));
    }
    return result;
  }
}
