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
}