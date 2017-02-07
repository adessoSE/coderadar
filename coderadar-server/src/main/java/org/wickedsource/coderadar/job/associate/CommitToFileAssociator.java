package org.wickedsource.coderadar.job.associate;

import java.util.*;
import java.util.stream.Collectors;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wickedsource.coderadar.analyzer.api.ChangeType;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.commit.domain.CommitToFileAssociation;
import org.wickedsource.coderadar.commit.domain.CommitToFileAssociationRepository;
import org.wickedsource.coderadar.commit.event.CommitToFileAssociatedEvent;
import org.wickedsource.coderadar.file.domain.*;

@Service
public class CommitToFileAssociator {

  private Logger logger = LoggerFactory.getLogger(CommitToFileAssociator.class);

  private GitLogEntryRepository gitLogEntryRepository;

  private FileRepository fileRepository;

  private CommitRepository commitRepository;

  private CommitToFileAssociationRepository commitToFileAssociationRepository;

  private ApplicationEventPublisher eventPublisher;

  private Meter commitsMeter;

  private Meter filesMeter;

  private static List<ChangeType> ALL_BUT_DELETED =
      Arrays.asList(
          ChangeType.ADD,
          ChangeType.RENAME,
          ChangeType.COPY,
          ChangeType.MODIFY,
          ChangeType.UNCHANGED);

  @Autowired
  public CommitToFileAssociator(
          GitLogEntryRepository gitLogEntryRepository,
          FileRepository fileRepository,
          CommitRepository commitRepository,
          CommitToFileAssociationRepository commitToFileAssociationRepository,
          ApplicationEventPublisher eventPublisher, MetricRegistry metricRegistry) {
    this.gitLogEntryRepository = gitLogEntryRepository;
    this.fileRepository = fileRepository;
    this.commitRepository = commitRepository;
    this.commitToFileAssociationRepository = commitToFileAssociationRepository;
    this.eventPublisher = eventPublisher;
    this.commitsMeter = metricRegistry.meter("coderadar.CommitToFileAssociator.commits");
    this.filesMeter = metricRegistry.meter("coderadar.CommitToFileAssociator.files");
  }

  /**
   * Goes through the git log of all files of the given commit and creates {@link File} entities for
   * each. If a {@link File} for the file is already present, the file in the commit is associated
   * with the present to file so that RENAMED files can be traced to their new filename in the
   * database. Each {@link File} entity will then be associated with the {@link Commit} entity in
   * order to build a searchable database.
   *
   * @param commit the commit whose files to associate
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void associateFilesOfCommit(Commit commit) {
    checkEligibility(commit);
    logger.debug("starting associating files of commit {}", commit);

    int addedFilesCount = associateWithAddedAndCopiedFiles(commit);
    filesMeter.mark(addedFilesCount);

    List<File> modifiedFiles = associateWithModifiedFiles(commit);
    filesMeter.mark(modifiedFiles.size());

    List<File> renamedFiles = associateWithRenamedFiles(commit);
    filesMeter.mark(renamedFiles.size());

    List<File> deletedFiles = associateWithDeletedFiles(commit);
    filesMeter.mark(deletedFiles.size());

    int unchangedFilesCount = associateWithUnchangedFiles(commit, modifiedFiles, renamedFiles, deletedFiles);
    filesMeter.mark(unchangedFilesCount);

    commit.setMerged(true);
    commitRepository.save(commit);
    // commit entity is updated in database implicitly
    commitsMeter.mark();
  }

  private List<File> associateWithRenamedFiles(Commit commit) {
    List<GitLogEntry> renamedFiles =
        gitLogEntryRepository.findByCommitNameAndChangeTypeIn(
            commit.getName(), Collections.singletonList(ChangeType.RENAME));

    Map<String, GitLogEntry> oldPathToFile = new HashMap<>();
    for (GitLogEntry file : renamedFiles) {
      oldPathToFile.put(file.getOldFilepath(), file);
    }

    logger.debug("found {} RENAMED files", renamedFiles.size());
    List<String> oldFilepaths =
        renamedFiles.stream().map(GitLogEntry::getOldFilepath).collect(Collectors.toList());

    // the File entities of RENAMED files must already exist in the commit before the current commit, so
    // we simply load them from the database
    List<File> filesToAssociate =
        fileRepository.findInCommit(commit.getFirstParent(), oldFilepaths);

    for (File file : filesToAssociate) {
      File newFile = new File();
      newFile.setIdentity(file.getIdentity());
      newFile.setFilepath(oldPathToFile.get(file.getFilepath()).getFilepath());
      fileRepository.save(newFile);
      CommitToFileAssociation association =
          new CommitToFileAssociation(commit, newFile, ChangeType.RENAME);
      commit.getFiles().add(association);
      saveCommitToFileAssociation(association);
    }
    return filesToAssociate;
  }

  private List<File> associateWithModifiedFiles(Commit commit) {
    List<GitLogEntry> modifiedFiles =
        gitLogEntryRepository.findByCommitNameAndChangeTypeIn(
            commit.getName(), Collections.singletonList(ChangeType.MODIFY));
    logger.debug("found {} MODIFIED files", modifiedFiles.size());
    List<String> filepaths =
        modifiedFiles.stream().map(GitLogEntry::getFilepath).collect(Collectors.toList());

    // the File entities of MODIFIED files must already exist in the commit before the current commit, so
    // we simply load them from the database
    List<File> filesToAssociate = fileRepository.findInCommit(commit.getFirstParent(), filepaths);

    for (File file : filesToAssociate) {
      CommitToFileAssociation association =
          new CommitToFileAssociation(commit, file, ChangeType.MODIFY);
      commit.getFiles().add(association);
      saveCommitToFileAssociation(association);
    }
    return filesToAssociate;
  }

  private List<File> associateWithDeletedFiles(Commit commit) {
    List<GitLogEntry> deletedFiles =
        gitLogEntryRepository.findByCommitNameAndChangeTypeIn(
            commit.getName(), Collections.singletonList(ChangeType.DELETE));
    logger.debug("found {} DELETED files", deletedFiles.size());
    List<String> filepaths =
        deletedFiles.stream().map(GitLogEntry::getOldFilepath).collect(Collectors.toList());

    // the File entities of DELETED files must already exist in the commit before the current commit, so
    // we simply load them from the database
    List<File> filesToAssociate = fileRepository.findInCommit(commit.getFirstParent(), filepaths);

    for (File file : filesToAssociate) {
      CommitToFileAssociation association =
          new CommitToFileAssociation(commit, file, ChangeType.DELETE);
      commit.getFiles().add(association);
      saveCommitToFileAssociation(association);
    }
    return filesToAssociate;
  }

  private int associateWithAddedAndCopiedFiles(Commit commit) {
    List<GitLogEntry> addedFiles =
        gitLogEntryRepository.findByCommitNameAndChangeTypeIn(
            commit.getName(), Arrays.asList(ChangeType.ADD, ChangeType.COPY));
    logger.debug("found {} ADDED and COPIED files", addedFiles.size());
    for (GitLogEntry addedFile : addedFiles) {
      File file = new File();
      file.setFilepath(addedFile.getFilepath());
      fileRepository.save(file);
      file.setIdentity(new FileIdentity());
      CommitToFileAssociation association =
          new CommitToFileAssociation(commit, file, addedFile.getChangeType());
        commit.getFiles().add(association);
        saveCommitToFileAssociation(association);
    }
    return addedFiles.size();
  }

  private int associateWithUnchangedFiles(
      Commit commit, List<File> modifiedFiles, List<File> renamedFiles, List<File> deletedFiles) {
    List<File> unchangedFiles =
        fileRepository.findInCommit(
            ALL_BUT_DELETED, commit.getFirstParent(), commit.getProject().getId());
    unchangedFiles.removeAll(modifiedFiles);
    unchangedFiles.removeAll(renamedFiles);
    unchangedFiles.removeAll(deletedFiles);
    logger.debug("found {} UNCHANGED files", unchangedFiles.size());
    for (File file : unchangedFiles) {
      CommitToFileAssociation association =
          new CommitToFileAssociation(commit, file, ChangeType.UNCHANGED);
        commit.getFiles().add(association);
        saveCommitToFileAssociation(association);
    }
    return unchangedFiles.size();
  }

  private void saveCommitToFileAssociation(CommitToFileAssociation association) {
    eventPublisher.publishEvent(new CommitToFileAssociatedEvent(association));
    commitToFileAssociationRepository.save(association);
  }

  private void checkEligibility(Commit commit) {
    if (commit.isMerged()) {
      throw new IllegalStateException(String.format("commit is already merged: %s", commit));
    }
    if (!commit.isScanned()) {
      throw new IllegalStateException(String.format("commit is not yet scanned: %s", commit));
    }
  }
}
