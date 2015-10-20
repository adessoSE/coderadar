package org.wickedsource.coderadar.analyzer.analyze;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.gitective.core.BlobUtils;
import org.gitective.core.CommitUtils;
import org.gitective.core.TreeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.coderadar.analyzer.plugin.api.AnalyzerException;
import org.wickedsource.coderadar.analyzer.plugin.api.AnalyzerFilter;
import org.wickedsource.coderadar.analyzer.plugin.api.AnalyzerPlugin;
import org.wickedsource.coderadar.analyzer.plugin.api.FileMetrics;

import java.util.ArrayList;
import java.util.List;

/**
 * Collects metrics to all files that are part of a single commit/revision in a given git repository.
 */
public class SingleCommitAnalyzer {

    private Logger logger = LoggerFactory.getLogger(SingleCommitAnalyzer.class);

    private List<AnalyzerPlugin> plugins = new ArrayList<>();

    private Git gitRepository;

    public SingleCommitAnalyzer(List<AnalyzerPlugin> plugins, Git gitRepository) {
        this.plugins = plugins;
        this.gitRepository = gitRepository;
    }

    public FileSetMetrics analyzeCodebase(String commitHash) {
        final FileSetMetrics fileSetMetrics = new FileSetMetrics();
        final ObjectId commitId = CommitUtils.getCommit(gitRepository.getRepository(), commitHash);
        RevCommit commit = CommitUtils.getCommit(gitRepository.getRepository(), commitId);
        RevTree commitTree = commit.getTree();
        TreeUtils.visit(gitRepository.getRepository(), commitTree.getId(), new TreeUtils.ITreeVisitor() {
            @Override
            public boolean accept(FileMode mode, String path, String name, AnyObjectId id) {
                String filePath = path + "/" + name;
                try {
                    FileMetrics fileMetrics = analyzeFile(commitId, mode, filePath, id);
                    fileSetMetrics.addMetricsToFile(filePath, fileMetrics);
                } catch (FileSkippedException | AnalyzerException e) {
                    // do nothing, just skip this file
                    logger.debug("skipped file {} due to {} with message '{}'", filePath, e.getClass().getSimpleName(), e.getMessage());
                }
                return true;
            }
        });
        return fileSetMetrics;
    }

    private FileMetrics analyzeFile(ObjectId commitId, FileMode mode, String filePath, AnyObjectId id) throws AnalyzerException, FileSkippedException {
        if (!isAboveThreshold(filePath)) {
            FileMetrics fileMetrics = new FileMetrics();
            for (AnalyzerPlugin plugin : plugins) {
                if (acceptFile(plugin.getFilter(), filePath)) {
                    byte[] fileContent = BlobUtils.getRawContent(gitRepository.getRepository(), commitId, filePath);
                    fileMetrics.add(plugin.analyzeFile(fileContent));
                }
            }
            return fileMetrics;
        } else {
            throw new FileSkippedException("file is too large to analyze");
        }
    }

    private boolean acceptFile(AnalyzerFilter filter, String filePath) {
        return (filter.acceptBinary() | !isBinary(filePath)) && filter.acceptFilename(filePath);
    }

    /**
     * Determines whether the given file is in binary format.
     */
    private boolean isBinary(String filePath) {
        // TODO: analyze the first couple bytes of the file to determine if it's binary
        return false;
    }

    /**
     * Determines whether the given file is too large to analyze.
     */
    private boolean isAboveThreshold(String filePath) {
        // TODO: how to get the size of the file without loading it into memory or walking all the way through a stream?
        return false;
    }

    private class FileSkippedException extends Exception {

        public FileSkippedException(String message){
            super(message);
        }

    }

}
