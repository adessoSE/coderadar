package io.reflectoring.coderadar.contributor.port.driver;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import java.util.List;

public interface GetContributorsUseCase {
  List<Contributor> getContributors(Long projectId);

  List<Contributor> getContributorsForProjectAndFilename(
      Long projectId, GetForFilenameCommand command);
}
