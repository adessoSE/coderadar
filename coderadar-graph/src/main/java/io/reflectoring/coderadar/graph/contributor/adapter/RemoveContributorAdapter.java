package io.reflectoring.coderadar.graph.contributor.adapter;

import io.reflectoring.coderadar.contributor.port.driven.RemoveContributorPort;
import io.reflectoring.coderadar.domain.Contributor;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveContributorAdapter implements RemoveContributorPort {

  private final ContributorRepository contributorRepository;

  @Override
  public void removeContributorFromProject(Contributor contributor, long projectId) {
    contributorRepository.detachContributorFromProject(contributor.getId(), projectId);
  }

  @Override
  public void removeContributorsFromProject(List<Contributor> contributors, long projectId) {
    List<Long> ids = new ArrayList<>(contributors.size());
    contributors.forEach(contributor -> ids.add(contributor.getId()));
    contributorRepository.detachContributorsFromProject(ids, projectId);
  }
}
