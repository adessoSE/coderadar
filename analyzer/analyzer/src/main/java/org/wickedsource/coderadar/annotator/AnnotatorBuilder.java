package org.wickedsource.coderadar.annotator;

import java.io.File;
import java.util.Properties;

public class AnnotatorBuilder {

    private Properties properties = new Properties();

    private Annotator.VcsType vcsType = Annotator.VcsType.GIT;

    private String repositoryUrl;

    private File localRepositoryFolder;

    /**
     * Sets the properties that are used to configure the analyzers.
     * OPTIONAL parameter. By default, empty properties will be used.
     *
     * @param properties the properties to configure the analyzers
     * @return this builder.
     */
    public AnnotatorBuilder setProperties(Properties properties) {
        this.properties = properties;
        return this;
    }

    /**
     * Sets the type of the remote vcs repository.
     * OPTIONAL parameter. By default, Git is assumed as type of vcs.
     *
     * @param vcsType the type of the remote vcs repository.
     * @return this builder.
     */
    public AnnotatorBuilder setVcsType(Annotator.VcsType vcsType) {
        this.vcsType = vcsType;
        return this;
    }

    /**
     * Sets the url of the remote vcs repository.
     * MANDATORY parameter.
     *
     * @param repositoryUrl url of the remote vcs repository.
     * @return this builder.
     */
    public AnnotatorBuilder setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
        return this;
    }

    /**
     * Sets the local folder where the local copy of the remote repository will be stored.
     * MANDATORY parameter.
     *
     * @param localRepositoryFolder local folder where to store the local copy of the remote repository.
     * @return this builder.
     */
    public AnnotatorBuilder setLocalRepositoryFolder(File localRepositoryFolder) {
        this.localRepositoryFolder = localRepositoryFolder;
        return this;
    }

    public Annotator build() {
        if (repositoryUrl == null || "".equals(repositoryUrl)) {
            throw new IllegalArgumentException("Parameter 'repositoryUrl' must be specified!");
        }
        if (localRepositoryFolder == null) {
            throw new IllegalArgumentException("Parameter 'localRepositoryFoler' must be specified!");
        }
        return new Annotator(properties, vcsType, repositoryUrl, localRepositoryFolder);
    }

}
