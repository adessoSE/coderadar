package io.reflectoring.coderadar.rest;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.rest.domain.GetFilePatternResponse;

import java.util.ArrayList;
import java.util.List;

public class GetFilePatternResponseMapper {
  private GetFilePatternResponseMapper() {}

  public static GetFilePatternResponse mapFilePattern(FilePattern filePattern) {
    return new GetFilePatternResponse(
        filePattern.getId(), filePattern.getPattern(), filePattern.getInclusionType());
  }

  public static List<GetFilePatternResponse> mapFilePatterns(List<FilePattern> filePatterns) {
    List<GetFilePatternResponse> responses = new ArrayList<>(filePatterns.size());
    for (FilePattern filePattern : filePatterns) {
      responses.add(mapFilePattern(filePattern));
    }
    return responses;
  }
}
