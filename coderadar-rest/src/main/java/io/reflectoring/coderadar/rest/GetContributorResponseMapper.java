package io.reflectoring.coderadar.rest;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.rest.domain.GetContributorResponse;

import java.util.ArrayList;
import java.util.List;

public class GetContributorResponseMapper {
  private GetContributorResponseMapper() {}

  public static GetContributorResponse mapContributor(Contributor contributor) {
    return new GetContributorResponse(
        contributor.getId(),
        contributor.getDisplayName(),
        contributor.getNames(),
        contributor.getEmailAddresses());
  }

  public static List<GetContributorResponse> mapContributors(List<Contributor> contributors) {
    List<GetContributorResponse> result = new ArrayList<>(contributors.size());
    for (Contributor c : contributors) {
      result.add(mapContributor(c));
    }
    return result;
  }
}
