package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import java.util.ArrayList;
import java.util.List;

public class SaveContributorsAdapter {

  public void saveContributors(List<Contributor> contributors) {
    List<ContributorEntity> contributorEntities = new ArrayList<>(contributors.size());
    for (Contributor c : contributors) {}
  }
}
