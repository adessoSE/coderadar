package io.reflectoring.coderadar.contributor.port.driven;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import java.util.List;

public interface SaveContributorsPort {
  List<Contributor> save(List<Contributor> contributors, Long projectId);
}
