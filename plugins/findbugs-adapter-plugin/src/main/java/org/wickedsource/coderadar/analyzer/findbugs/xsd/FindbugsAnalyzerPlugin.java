package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import edu.umd.cs.findbugs.BugCollection;
import edu.umd.cs.findbugs.*;
import edu.umd.cs.findbugs.config.UserPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.coderadar.analyzer.api.AnalyzerException;
import org.wickedsource.coderadar.analyzer.api.ByteCodeAnalyzerPlugin;
import org.wickedsource.coderadar.analyzer.api.FileSetMetrics;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class FindbugsAnalyzerPlugin implements ByteCodeAnalyzerPlugin {

    private Logger logger = LoggerFactory.getLogger(FindbugsAnalyzerPlugin.class);

    @Override
    public FileSetMetrics analyzeBytecodeFiles(List<File> bytecodeFiles) throws AnalyzerException {
        try {

            FindBugsProgress progress = new FindBugsProgress() {
                @Override
                public void reportNumberOfArchives(int numArchives) {

                }

                @Override
                public void startArchive(String name) {

                }

                @Override
                public void finishArchive() {

                }

                @Override
                public void predictPassCount(int[] classesPerPass) {

                }

                @Override
                public void startAnalysis(int numClasses) {

                }

                @Override
                public void finishClass() {

                }

                @Override
                public void finishPerClassAnalysis() {

                }
            };

            Project project = new Project();
            project.setProjectName("Coderadar Findbugs Analysis");
            for (File file : bytecodeFiles) {
                project.addFile(file.getAbsolutePath());
            }
            BugCollection bugCollection = doAnalysis(project, progress);
            logger.info(bugCollection.toString());

            return null;
        } catch (Exception e) {
            throw new AnalyzerException(e);
        }

    }

    public edu.umd.cs.findbugs.BugCollection doAnalysis(Project p, FindBugsProgress progressCallback) throws IOException,
            InterruptedException {
        StringWriter stringWriter = new StringWriter();
        BugCollectionBugReporter pcb = new BugCollectionBugReporter(p, new PrintWriter(stringWriter, true));
        pcb.setPriorityThreshold(Priorities.LOW_PRIORITY);
        IFindBugsEngine fb = createEngine(p, pcb);
        fb.setUserPreferences(UserPreferences.createDefaultUserPreferences());
        fb.setProgressCallback(progressCallback);
        fb.setProjectName(p.getProjectName());
        fb.execute();
        String warnings = stringWriter.toString();
        if (warnings.length() > 0) {
            logger.warn("analysis warnings:", warnings);
        }

        return pcb.getBugCollection();
    }

    private static IFindBugsEngine createEngine(@Nonnull Project p, BugReporter pcb) {
        FindBugs2 engine = new FindBugs2();
        engine.setBugReporter(pcb);
        engine.setProject(p);
        engine.setDetectorFactoryCollection(DetectorFactoryCollection.instance());
        return engine;
    }

}
