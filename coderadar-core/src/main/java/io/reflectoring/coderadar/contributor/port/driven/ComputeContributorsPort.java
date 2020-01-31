package io.reflectoring.coderadar.contributor.port.driven;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import java.util.List;

public interface ComputeContributorsPort {
  List<Contributor> computeContributors(Long projectId, String repositoryRoot);
}
