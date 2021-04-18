package io.reflectoring.coderadar.vcs.adapter;

import com.google.common.collect.Sets;
import io.reflectoring.coderadar.contributor.port.driven.ComputeContributorsPort;
import io.reflectoring.coderadar.domain.Contributor;
import io.reflectoring.coderadar.domain.DateRange;
import io.reflectoring.coderadar.vcs.RevCommitHelper;
import java.util.*;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Service;

@Service
public class ComputeContributorAdapter implements ComputeContributorsPort {
  @Override
  public List<Contributor> computeContributors(
      String repositoryRoot, List<Contributor> existingContributors, DateRange dateRange) {
    List<RevCommit> revCommits =
        RevCommitHelper.getRevCommitsInDateRange(repositoryRoot, dateRange);

    Map<String, Set<String>> newContributors = new HashMap<>();

    for (RevCommit rc : revCommits) {
      PersonIdent author = rc.getAuthorIdent();
      String name = author.getName();
      String email = author.getEmailAddress().toLowerCase();
      if (newContributors.containsKey(email)) {
        newContributors.get(email).add(name);
      } else {
        newContributors.put(email, new HashSet<>(Collections.singletonList(name)));
      }
    }
    return mergeExistingContributors(existingContributors, newContributors);
  }

  private List<Contributor> mergeExistingContributors(
      List<Contributor> existingContributors, Map<String, Set<String>> newContributors) {
    Set<Contributor> contributors = Sets.newHashSetWithExpectedSize(newContributors.size());
    for (Map.Entry<String, Set<String>> entry : newContributors.entrySet()) {
      boolean alreadyAdded = false;
      for (Contributor c : existingContributors) {
        if (c.getEmailAddresses().contains(entry.getKey())) {
          c.getNames().addAll(entry.getValue());
          contributors.add(c);
          alreadyAdded = true;
          break;
        }
      }
      if (alreadyAdded) {
        continue;
      }
      Contributor contributor = new Contributor();
      contributor.setDisplayName(entry.getValue().iterator().next());
      contributor.setNames(entry.getValue());
      contributor.setEmailAddresses(new HashSet<>(Collections.singletonList(entry.getKey())));
      contributors.add(contributor);
    }
    List<Contributor> result = new ArrayList<>(contributors);
    result.sort(Comparator.comparing(Contributor::getDisplayName));
    return result;
  }
}
