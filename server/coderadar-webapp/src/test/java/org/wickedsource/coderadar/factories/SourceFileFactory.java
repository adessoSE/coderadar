package org.wickedsource.coderadar.factories;

import org.wickedsource.coderadar.metric.domain.SourceFile;
import org.wickedsource.coderadar.metric.domain.SourceFileIdentity;

public class SourceFileFactory {

    public SourceFile withPath(String filepath){
        SourceFile file = new SourceFile();
        file.setIdentity(new SourceFileIdentity());
        file.setFilepath(filepath);
        return file;
    }
}
