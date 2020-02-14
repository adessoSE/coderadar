package io.reflectoring.coderadar.contributor.port.driven;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import java.util.List;

public interface GetContributorPort {
  Contributor findById(Long id);

  List<Contributor> findAll();

  List<Contributor> findAllByProjectId(Long projectId);

  List<Contributor> findAllByProjectIdAndFilename(Long projectId, String filename);
}
