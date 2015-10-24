package org.wickedsource.coderadar.annotator;

import java.io.File;

public class AnnotatorManualTest {

    public static void main(String[] args) {
        AnnotatorBuilder builder = new AnnotatorBuilder();
        Annotator annotator = builder
                .setLocalRepositoryFolder(new File("D:\\TestRepo"))
                .setRepositoryUrl("https://github.com/thombergs/coderadar.git")
                .setVcsType(Annotator.VcsType.GIT)
                .build();
        annotator.annotate();
    }
}
