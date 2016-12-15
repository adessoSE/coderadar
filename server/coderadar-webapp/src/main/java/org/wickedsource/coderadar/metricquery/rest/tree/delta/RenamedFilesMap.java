package org.wickedsource.coderadar.metricquery.rest.tree.delta;

import java.util.HashMap;
import java.util.Map;

public class RenamedFilesMap {

    private Map<String, String> newFileNames = new HashMap<>();

    private Map<String, String> oldFileNames = new HashMap<>();

    public void addRenamedFile(String oldFileName, String newFileName) {
        newFileNames.put(oldFileName, newFileName);
        oldFileNames.put(newFileName, oldFileName);
    }

    public String getNewFileName(String filepath) {
        return newFileNames.get(filepath);
    }

    public String getOldFileName(String filepath) {
        return oldFileNames.get(filepath);
    }

}
