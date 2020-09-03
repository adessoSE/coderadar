package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.ChangeTypeMapper;
import io.reflectoring.coderadar.vcs.UnableToGetDiffsFromCommitsException;
import io.reflectoring.coderadar.vcs.domain.DiffEntry;
import io.reflectoring.coderadar.vcs.port.driven.GetDiffEntriesForCommitsPort;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.springframework.stereotype.Service;

@Service
public class GetDiffEntriesForCommitsAdapter implements GetDiffEntriesForCommitsPort {
  @Override
  public List<DiffEntry> getDiffs(String projectRoot, String commitName1, String commitName2)
      throws UnableToGetDiffsFromCommitsException {
    try (Git git = Git.open(new File(projectRoot))) {
      Repository repository = git.getRepository();
      RevCommit commit1 = repository.parseCommit(ObjectId.fromString(commitName1));
      RevCommit commit2 = repository.parseCommit(ObjectId.fromString(commitName2));
      // change commits so that commit2 is the older one
      if (commit1.getCommitTime() > commit2.getCommitTime()) {
        RevCommit tmp = commit1;
        commit1 = commit2;
        commit2 = tmp;
      }

      CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
      CanonicalTreeParser newTreeIter = new CanonicalTreeParser();

      try (ObjectReader reader = git.getRepository().newObjectReader()) {
        ObjectId oldTree = commit1.getTree();
        oldTreeIter.reset(reader, oldTree);

        ObjectId newTree = commit2.getTree();
        newTreeIter.reset(reader, newTree);
      }

      DiffFormatter df = new DiffFormatter(new ByteArrayOutputStream());
      df.setRepository(repository);
      return df.scan(oldTreeIter, newTreeIter).stream()
          .map(
              diffEntry -> {
                DiffEntry entry = new DiffEntry();
                entry.setNewPath(diffEntry.getNewPath());
                entry.setOldPath(diffEntry.getOldPath());
                entry.setChangeType(
                    ChangeTypeMapper.jgitToCoderadar(diffEntry.getChangeType()).ordinal());
                return entry;
              })
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new UnableToGetDiffsFromCommitsException(e.getMessage());
    }
  }
}
