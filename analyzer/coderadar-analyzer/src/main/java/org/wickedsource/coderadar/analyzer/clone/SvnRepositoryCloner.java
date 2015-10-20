package org.wickedsource.coderadar.analyzer.clone;

import org.apache.commons.exec.*;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 * Clones a remote SVN repository into a local Git repository.
 * </p>
 * <p/>
 * <strong>Attention!</strong> A version of git supporting the "git svn" command has to be installed on the machine in
 * order for this class to work. Also, all files in the target folder the repository is cloned into will be deleted!
 * </p>
 */
public class SvnRepositoryCloner implements RepositoryCloner {

    private Logger logger = LoggerFactory.getLogger(SvnRepositoryCloner.class);

    public Git cloneRepository(String remoteUrl, File localDir) {
        try {
            if(localDir.exists()){
                if(localDir.isDirectory()) {
                    logger.debug("deleting existing directory {}", localDir.getAbsolutePath());
                    FileUtils.deleteDirectory(localDir);
                }else if(localDir.isFile()) {
                    FileUtils.deleteQuietly(localDir);
                    logger.debug("deleting existing file {}", localDir.getAbsolutePath());
                }
            }
            logger.info("cloning remote SVN repository from '{}' to '{}' ... this may take a while", remoteUrl, localDir.getAbsoluteFile());
            String command = String.format("%s %s", "git svn clone", remoteUrl);
            CommandLine cmdLine = CommandLine.parse(command);
            DefaultExecutor executor = new DefaultExecutor();
            LogOutputStream out = new LogOutputStream() {
                @Override
                protected void processLine(String line, int logLevel) {
                    logger.debug(line);
                }
            };
            PumpStreamHandler handler = new PumpStreamHandler(out);
            executor.setWorkingDirectory(localDir);
            executor.setStreamHandler(handler);
            localDir.mkdirs();
            logger.debug("executing command '{}'", command);
            int exitValue = executor.execute(cmdLine);

            if (exitValue != 0) {
                throw new RuntimeException(String.format("'git svn' command did not exit normally. Exit value: %s", exitValue));
            }

            // If the remoteUrl is ".../trunk" the git repository is created at localDir/trunk.
            // Thus, we open the git repository at the first subdirectory of localDir which is the last element of the remote path
            Git git = Git.open(new File(localDir, getLastPathElement(remoteUrl)));
            return git;
        } catch (ExecuteException e) {
            throw new RuntimeException("error executing command 'git svn'", e);
        } catch (IOException e) {
            throw new RuntimeException("error executing command 'git svn' ... is the git executable included in the environment?", e);
        }
    }

    protected String getLastPathElement(String path){
        return path.substring(path.lastIndexOf("/"));
    }

}
