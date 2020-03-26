package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.branch.repository.BranchRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.BranchEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.query.domain.CommitLog;
import io.reflectoring.coderadar.query.domain.CommitLogAuthor;
import io.reflectoring.coderadar.query.port.driven.GetCommitLogPort;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class GetCommitLogAdapter implements GetCommitLogPort {

  private final CommitRepository commitRepository;
  private final BranchRepository branchRepository;

  public GetCommitLogAdapter(CommitRepository commitRepository, BranchRepository branchRepository) {
    this.commitRepository = commitRepository;
    this.branchRepository = branchRepository;
  }

  @Override
  public List<CommitLog> getCommitLog(long projectId) {
    List<BranchEntity> branches = branchRepository.getBranchesInProject(projectId);
    List<LinkedHashMap<String, Object>> commits =
        commitRepository.findByProjectIdWithParents(projectId);
    List<CommitLog> log = new ArrayList<>();

    Map<String, List<String>> commitToRefMap = new HashMap<>();
    for (BranchEntity ref : branches) {
      String commitName = ref.getCommitHash();
      List<String> refNames = commitToRefMap.computeIfAbsent(commitName, k -> new ArrayList<>());
      if (!refNames.contains(ref.getName())) {
        refNames.add(ref.getName());
      }
    }
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
              .setHash(commit.getName())
              .setSubject(
                  commit.getComment().substring(0, Math.min(100, commit.getComment().length())))
              .setAuthor(author)
              .setAnalyzed(commit.isAnalyzed());

      Object[] parents = (Object[]) commitWithParents.get("parents");
      if (parents.length > 0) {
        commitLog.setParents((String[]) parents);
      }

      List<String> refsOnCommit = commitToRefMap.get(commit.getName());
      if (refsOnCommit != null) {
        commitLog.setRefs(refsOnCommit);
      }
      log.add(commitLog);
    }
    return log;
  }
}
