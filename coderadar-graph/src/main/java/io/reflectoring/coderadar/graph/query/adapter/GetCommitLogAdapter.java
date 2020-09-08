package io.reflectoring.coderadar.graph.query.adapter;

import com.google.common.collect.Maps;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.branch.repository.BranchRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.BranchEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.query.domain.CommitLog;
import io.reflectoring.coderadar.query.domain.CommitLogAuthor;
import io.reflectoring.coderadar.query.port.driven.GetCommitLogPort;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCommitLogAdapter implements GetCommitLogPort {

  private final CommitRepository commitRepository;
  private final BranchRepository branchRepository;

  @Override
  public List<CommitLog> getCommitLog(long projectId) {
    List<BranchEntity> branches = branchRepository.getBranchesInProject(projectId);
    List<LinkedHashMap<String, Object>> commits =
        commitRepository.findByProjectIdWithParents(projectId);

    Map<String, List<String>> commitToRefMap = Maps.newHashMapWithExpectedSize(branches.size());
    for (BranchEntity ref : branches) {
      // most commits will have at most 2 branches/tags on them
      List<String> refNames =
          commitToRefMap.computeIfAbsent(ref.getCommitHash(), k -> new ArrayList<>(2));
      if (!refNames.contains(ref.getName())) {
        if (ref.isTag()) {
          refNames.add("tag: " + ref.getName());
        } else {
          refNames.add(ref.getName());
        }
      }
    }

    List<CommitLog> log = new ArrayList<>(commits.size());
    for (LinkedHashMap<String, Object> commitWithParents : commits) {
      CommitEntity commit = (CommitEntity) commitWithParents.get("commit");
      // author
      CommitLogAuthor author =
          new CommitLogAuthor()
              .setName(commit.getAuthor())
              .setEmail(commit.getAuthorEmail())
              .setTimestamp(commit.getTimestamp());

      CommitLog commitLog =
          new CommitLog()
              .setHash(commit.getHash())
              .setSubject(
                  commit.getComment().substring(0, Math.min(100, commit.getComment().length())))
              .setAuthor(author)
              .setAnalyzed(commit.isAnalyzed());

      Object[] parents = (Object[]) commitWithParents.get("parents");
      if (parents.length > 0) {
        commitLog.setParents((String[]) parents);
      } else {
        commitLog.setParents(new String[0]);
      }

      List<String> refsOnCommit = commitToRefMap.get(commit.getHash());
      if (refsOnCommit != null) {
        commitLog.setRefs(refsOnCommit);
      }
      log.add(commitLog);
    }
    return log;
  }
}
