package org.wickedsource.coderadar.annotator;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.wickedsource.coderadar.analyzer.checkstyle.CheckstyleAnalyzerPlugin;
import org.wickedsource.coderadar.annotator.annotate.NoteUtil;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class AnnotatorManualTest {

    public static void main(String[] args) throws IOException {

        Properties properties = new Properties();
        properties.put(CheckstyleAnalyzerPlugin.class.getName() + ".configLocation", "src/test/resources/checkstyle.xml");

        AnnotatorBuilder builder = new AnnotatorBuilder();
        Annotator annotator = builder
                .setLocalRepositoryFolder(new File("D:\\TestRepo"))
                .setRepositoryUrl("https://github.com/thombergs/coderadar.git")
                .setVcsType(Annotator.VcsType.GIT)
                .setProperties(properties)
                .build();
        annotator.annotate();

        Git gitClient = Git.open(new File("D://TestRepo"));

        ObjectId lastCommitId = gitClient.getRepository().resolve(Constants.HEAD);
        RevWalk revWalk = new RevWalk(gitClient.getRepository());
        RevCommit currentCommit = revWalk.parseCommit(lastCommitId);

        while (currentCommit != null) {

            System.out.println(NoteUtil.getInstance().getCoderadarNote(gitClient, currentCommit.getName()));

            if (currentCommit.getParentCount() > 0) {
                currentCommit = revWalk.parseCommit(currentCommit.getParent(0).getId()); //TODO: support multiple parents
            } else {
                currentCommit = null;
            }
        }
    }
}
