package io.reflectoring.coderadar.contributor.port.driven;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.query.domain.DateRange;

import java.util.List;

public interface ComputeContributorsPort {
  /**
   * Traverse through the git commit history and find all contributors that have worked on the
   * repository. Contributors are uniquely identified by their email-addresses. If there is a
   * contributor that we have already been saved earlier (i.e. this one exists in the given
   * parameter), do not create a new contributor, just create the relationship of this contributor
   * to the new project.
   *
   * @param repositoryRoot Repository root of the local repository.
   * @param existingContributors Contributors that were saved earlier.
   * @return List of contributors who worked on the specific repository.
   */
  List<Contributor> computeContributors(
          String repositoryRoot, List<Contributor> existingContributors, DateRange dateRange);
}
