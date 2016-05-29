package org.wickedsource.coderadar.job.execute.merge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wickedsource.coderadar.analyzer.api.ChangeType;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.commit.domain.CommitToSourceFileAssociation;
import org.wickedsource.coderadar.commit.domain.CommitToSourceFileAssociationRepository;
import org.wickedsource.coderadar.metric.domain.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommitMerger {

    private Logger logger = LoggerFactory.getLogger(CommitMerger.class);

    private ScannedSourceFileRepository scannedSourceFileRepository;

    private SourceFileRepository sourceFileRepository;

    private CommitRepository commitRepository;

    private CommitToSourceFileAssociationRepository associationRepository;

    private static List<ChangeType> ALL_BUT_DELETED = Arrays.asList(ChangeType.ADD, ChangeType.RENAME, ChangeType.COPY, ChangeType.MODIFY, ChangeType.UNCHANGED);

    @Autowired
    public CommitMerger(ScannedSourceFileRepository scannedSourceFileRepository, SourceFileRepository sourceFileRepository, CommitRepository commitRepository, CommitToSourceFileAssociationRepository associationRepository) {
        this.scannedSourceFileRepository = scannedSourceFileRepository;
        this.sourceFileRepository = sourceFileRepository;
        this.commitRepository = commitRepository;
        this.associationRepository = associationRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void mergeCommit(Commit commit) {
        checkEligibility(commit);
        logger.debug("starting merge of commit {}", commit);
        associateWithAddedAndCopiedFiles(commit);
        List<SourceFile> modifiedFiles = associateWithModifiedFiles(commit);
        List<SourceFile> renamedFiles = associateWithRenamedFiles(commit);
        associateWithUnchangedFiles(commit, modifiedFiles, renamedFiles);
        commit.setMerged(true);
        // commit is updated in database implicitly
    }

    private List<SourceFile> associateWithRenamedFiles(Commit commit) {
        List<ScannedSourceFile> renamedFiles = scannedSourceFileRepository.findByCommitNameAndChangeTypeIn(commit.getName(), Arrays.asList(ChangeType.RENAME));

        Map<String, ScannedSourceFile> oldPathToFile = new HashMap<>();
        for(ScannedSourceFile file : renamedFiles){
            oldPathToFile.put(file.getFilepathBeforeRename(), file);
        }

        logger.debug("found {} RENAMED files", renamedFiles.size());
        List<String> oldFilepaths = renamedFiles
                .stream()
                .map(ScannedSourceFile::getFilepathBeforeRename)
                .collect(Collectors.toList());
        List<SourceFile> filesToAssociate = sourceFileRepository.findInCommit(commit.getParentCommitName(), oldFilepaths);
        for (SourceFile file : filesToAssociate) {
            SourceFile newFile = new SourceFile();
            newFile.setIdentity(file.getIdentity());
            newFile.setFilepath(oldPathToFile.get(file.getFilepath()).getFilepath());
            sourceFileRepository.save(newFile);
            CommitToSourceFileAssociation association = new CommitToSourceFileAssociation(commit, newFile, ChangeType.RENAME);
            logger.debug(association.getId().toString());
            commit.getSourceFiles().add(association);
        }
        return filesToAssociate;
    }

    private List<SourceFile> associateWithModifiedFiles(Commit commit) {
        List<ScannedSourceFile> modifiedFiles = scannedSourceFileRepository.findByCommitNameAndChangeTypeIn(commit.getName(), Arrays.asList(ChangeType.MODIFY));
        logger.debug("found {} MODIFIED files", modifiedFiles.size());
        List<String> filepaths = modifiedFiles
                .stream()
                .map(ScannedSourceFile::getFilepath)
                .collect(Collectors.toList());
        List<SourceFile> filesToAssociate = sourceFileRepository.findInCommit(commit.getParentCommitName(), filepaths);
        for (SourceFile file : filesToAssociate) {
            CommitToSourceFileAssociation association = new CommitToSourceFileAssociation(commit, file, ChangeType.MODIFY);
            logger.debug(association.getId().toString());
            commit.getSourceFiles().add(association);
        }
        return filesToAssociate;
    }

    private void associateWithAddedAndCopiedFiles(Commit commit) {
        List<ScannedSourceFile> addedFiles = scannedSourceFileRepository.findByCommitNameAndChangeTypeIn(commit.getName(), Arrays.asList(ChangeType.ADD, ChangeType.COPY));
        logger.debug("found {} ADDED and COPIED files", addedFiles.size());
        for (ScannedSourceFile addedFile : addedFiles) {
            SourceFile sourceFile = new SourceFile();
            sourceFile.setFilepath(addedFile.getFilepath());
            sourceFileRepository.save(sourceFile);
            sourceFile.setIdentity(new SourceFileIdentity());
            CommitToSourceFileAssociation association = new CommitToSourceFileAssociation(commit, sourceFile, addedFile.getChangeType());
            logger.debug(association.getId().toString());
            if (!commit.getSourceFiles().contains(association)) {
                commit.getSourceFiles().add(association);
            }
        }
    }

    private void associateWithUnchangedFiles(Commit commit, List<SourceFile> modifiedFiles, List<SourceFile> renamedFiles) {
        List<SourceFile> unchangedFiles = sourceFileRepository.findInCommit(ALL_BUT_DELETED, commit.getParentCommitName());
        unchangedFiles.removeAll(modifiedFiles);
        unchangedFiles.removeAll(renamedFiles);
        logger.debug("found {} UNCHANGED files", unchangedFiles.size());
        for (SourceFile file : unchangedFiles) {
            CommitToSourceFileAssociation association = new CommitToSourceFileAssociation(commit, file, ChangeType.UNCHANGED);
            logger.debug(association.getId().toString());
            if (!commit.getSourceFiles().contains(association)) {
                commit.getSourceFiles().add(association);
            }
        }
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
