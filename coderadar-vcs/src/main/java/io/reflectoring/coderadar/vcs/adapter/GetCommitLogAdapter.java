package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.query.domain.CommitLog;
import io.reflectoring.coderadar.query.domain.CommitLogAuthor;
import io.reflectoring.coderadar.query.port.driven.GetCommitLogPort;
import io.reflectoring.coderadar.vcs.RevCommitHelper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GetCommitLogAdapter implements GetCommitLogPort {

  private final Logger logger = LoggerFactory.getLogger(GetCommitLogAdapter.class);

  @Override
  public List<CommitLog> getCommitLog(String path) {
    List<RevCommit> commits = RevCommitHelper.getRevCommits(path);
    List<CommitLog> log = new ArrayList<>();
    Map<String, List<String>> commitToRefMap = new HashMap<>();

    try (Git git = Git.open(new File(path))) {
      List<Ref> refs = git.getRepository().getRefDatabase().getRefs();
      for (Ref ref : refs) {
        String commitName = ref.getObjectId().name();
        List<String> refNames = commitToRefMap.computeIfAbsent(commitName, k -> new ArrayList<>());
        String[] split = ref.getName().split("/");
        String refName = split[split.length - 1];
        if (!refNames.contains(refName)) {
          refNames.add(split[split.length - 1]);
        }
      }
      for (RevCommit commit : commits) {

        // Get the parents
        List<String> parents = new ArrayList<>();
        for (RevCommit parent : commit.getParents()) {
          parents.add(parent.name());
        }

        // author
        PersonIdent authorIdent = commit.getCommitterIdent();
        CommitLogAuthor author =
            new CommitLogAuthor()
                .setName(authorIdent.getName())
                .setEmail(authorIdent.getEmailAddress())
                .setTimestamp(authorIdent.getWhen().getTime());

        CommitLog commitLog =
            new CommitLog()
                .setHash(commit.name())
                .setSubject(
                    commit
                        .getShortMessage()
                        .substring(0, Math.min(100, commit.getShortMessage().length())))
                .setAuthor(author)
                .setParents(parents);

        List<String> refsOnCommit = commitToRefMap.get(commit.name());
        if (refsOnCommit != null) {
          commitLog.setRefs(refsOnCommit);
        }
        log.add(commitLog);
      }
      return log;
    } catch (IOException e) {
      logger.error(String.format("Cannot create commit log: %s", e.getMessage()));
    }
    return new ArrayList<>();
  }
}
