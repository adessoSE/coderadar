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
  private final RevCommitHelper revCommitHelper;

  public ComputeContributorAdapter(RevCommitHelper revCommitHelper) {
    this.revCommitHelper = revCommitHelper;
  }

  @Override
  public List<Contributor> computeContributors(String repositoryRoot) {
    List<RevCommit> revCommits = revCommitHelper.getRevCommits(repositoryRoot);

    // key: name of contributor, value: left: email addresses, right: commit hashes
    Map<String, Pair<Set<String>, List<String>>> contributors = new HashMap<>();

    // key: email, value: contributor name ... we are saving the
    // email-addresses that we already saw and map them to the corresponding
    // contributor
    Map<String, String> emails = new HashMap<>();

    for (RevCommit rc : revCommits) {
      String name = rc.getAuthorIdent().getName();
      String email = rc.getAuthorIdent().getEmailAddress();

      if (emails.containsKey(email)) { // have i already seen this email address?
        String contributorName = emails.get(email); // if yes, get the corresponding contributor
        contributors
            .get(contributorName)
            .getLeft()
            .add(email); // add email to the email-addresses von this contributor
        contributors.get(contributorName).getRight().add(rc.getName());
        continue;
      } else {
        emails.put(email, name);
      }

      if (contributors.containsKey(name)) { // have i already seen this contributor?
        contributors
            .get(name)
            .getLeft()
            .add(email); // if yes, add email to the email set of this contributor
        contributors.get(name).getRight().add(rc.getName());
      } else {
        // contributors.put(name, new HashSet<>(Collections.singletonList(email)));
        contributors.put(
            name,
            Pair.of(
                new HashSet<>(Collections.singletonList(email)),
                new ArrayList<>(Collections.singletonList(rc.getName()))));
      }
    }

    // construct a list of contributors from the map
    List<Contributor> contributorsToSave = new ArrayList<>(contributors.size());
    for (Map.Entry<String, Pair<Set<String>, List<String>>> entry : contributors.entrySet()) {
      Contributor contributor =
          new Contributor()
              .setName(entry.getKey())
              .setEmails(entry.getValue().getLeft())
              .setCommitHashes(entry.getValue().getRight());
      contributorsToSave.add(contributor);
    }
    return contributorsToSave;
  }
}
