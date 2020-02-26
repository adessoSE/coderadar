package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.ComputeContributorsPort;
import io.reflectoring.coderadar.vcs.RevCommitHelper;
import java.util.*;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Service;

@Service
public class ComputeContributorAdapter implements ComputeContributorsPort {
  @Override
  public List<Contributor> computeContributors(String repositoryRoot) {
    List<RevCommit> revCommits = RevCommitHelper.getRevCommits(repositoryRoot);

    // key: contributor display name
    // left value: email addresses of this contributor
    // right value: all names
    Map<String, Pair<Set<String>, Set<String>>> contributors = new HashMap<>();

    Map<String, String> emails = new HashMap<>();

    for (RevCommit rc : revCommits) {
      String name = rc.getAuthorIdent().getName();
      String email = rc.getAuthorIdent().getEmailAddress();

      if (emails.containsKey(email)) {
        String contributorName = emails.get(email);
        contributors.get(contributorName).getLeft().add(email);
        contributors.get(contributorName).getRight().add(name);
        continue;
      } else {
        emails.put(email, name);
      }

      if (contributors.containsKey(name)) {
        contributors.get(name).getLeft().add(email);
      } else {
        contributors.put(
            name,
            Pair.of(
                new HashSet<>(Collections.singletonList(email)),
                new HashSet<>(Collections.singletonList(name))));
      }
    }

    // construct a list of contributors from the map
    List<Contributor> contributorsToSave = new ArrayList<>(contributors.size());
    for (Map.Entry<String, Pair<Set<String>, Set<String>>> entry : contributors.entrySet()) {
      Contributor contributor =
          new Contributor()
              .setDisplayName(entry.getKey())
              .setEmailAddresses(entry.getValue().getLeft())
              .setNames(entry.getValue().getRight());
      contributorsToSave.add(contributor);
    }
    return contributorsToSave;
  }
}
