package org.wickedsource.coderadar.metricquery.rest.tree.delta;

import org.wickedsource.coderadar.analyzer.api.ChangeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangedFilesMap {

    private Map<String, String> newFileNames = new HashMap<>();

    private Map<String, String> oldFileNames = new HashMap<>();

    private Map<String, List<ChangeType>> changeTypes = new HashMap<>();

    public void addRenamedFile(String oldFileName, String newFileName) {
        newFileNames.put(oldFileName, newFileName);
        oldFileNames.put(newFileName, oldFileName);
    }

    public void addChangeType(String filename, ChangeType changeType) {
        List<ChangeType> changeTypesList = this.changeTypes.get(filename);
        if (changeTypesList == null) {
            changeTypesList = new ArrayList<>();
            this.changeTypes.put(filename, changeTypesList);
        }
        changeTypesList.add(changeType);
    }

    public String getNewFileName(String filepath) {
        return newFileNames.get(filepath);
    }

    public String getOldFileName(String filepath) {
        return oldFileNames.get(filepath);
    }

    public List<ChangeType> getChangeTypes(String filepath) {
        return this.changeTypes.get(filepath);
    }

}
