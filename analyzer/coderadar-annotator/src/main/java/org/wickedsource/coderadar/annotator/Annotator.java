package org.wickedsource.coderadar.annotator;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.coderadar.analyzer.api.AnalyzerConfigurationException;
import org.wickedsource.coderadar.annotator.annotate.AnnotatingMetricsProcessor;
import org.wickedsource.coderadar.annotator.annotate.MetricsProcessor;
import org.wickedsource.coderadar.annotator.clone.GitRepositoryCloner;
import org.wickedsource.coderadar.annotator.clone.RepositoryCloner;
import org.wickedsource.coderadar.annotator.clone.SvnRepositoryCloner;
import org.wickedsource.coderadar.annotator.walk.AllCommitsWalker;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Controls the workflow of cloning a git repository, walking through it, analyzing the files on the way and annotating
 * the analysis results as notes into the git repository.
 */
public class Annotator {

    private Logger logger = LoggerFactory.getLogger(Annotator.class);

    private final Properties properties;

    private final VcsType vcsType;

    private final String repositoryUrl;

    private final File localRepositoryFolder;

    private final AnalyzerRegistry analyzerRegistry;

    public enum VcsType {
        SVN,
        GIT;
    }

    public Annotator(Properties properties, VcsType vcsType, String repositoryUrl, File localRepositoryFolder) {
        this.properties = properties;
        this.vcsType = vcsType;
        this.repositoryUrl = repositoryUrl;
        this.localRepositoryFolder = localRepositoryFolder;
        this.analyzerRegistry = new AnalyzerRegistry();
        try {
            analyzerRegistry.initializeAnalyzers(properties);
        } catch (AnalyzerConfigurationException e) {
            throw new RuntimeException("An analyzer could not be initialized correctly!", e);
        }
    }

    public void annotate() {
        try {
            // TODO: progress monitoring of each step
            if (localRepositoryFolder.exists()) {
                logger.warn("Target folder {} already exists. Deleting it now.", localRepositoryFolder);
                FileUtils.deleteDirectory(localRepositoryFolder);
            }
            Git gitClient = cloneRepository();
            annotateRepository(gitClient);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            analyzerRegistry.destroyAnalyzers();
        }
    }

    private Git cloneRepository() {
        RepositoryCloner cloner;
        if (vcsType == VcsType.SVN) {
            cloner = new SvnRepositoryCloner();
        } else {
            cloner = new GitRepositoryCloner();
        }
        return cloner.cloneRepository(repositoryUrl, localRepositoryFolder);
    }

    private void annotateRepository(Git gitClient) {
        AllCommitsWalker walker = new AllCommitsWalker();
        MetricsProcessor processor = new AnnotatingMetricsProcessor();
        walker.walk(gitClient, analyzerRegistry.getRegisteredAnalyzers(), processor);
    }

}
