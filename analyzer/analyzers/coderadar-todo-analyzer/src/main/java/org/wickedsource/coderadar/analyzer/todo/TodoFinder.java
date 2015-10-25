package org.wickedsource.coderadar.analyzer.todo;

import org.apache.commons.lang.StringUtils;
import org.wickedsource.coderadar.analyzer.api.Finding;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TodoFinder {

    private String[] todoPatterns;

    public TodoFinder(String... todoPatterns) {
        this.todoPatterns = todoPatterns;
    }

    public List<Finding> findTodos(byte[] fileContent) throws IOException {
        List<Finding> findings = new ArrayList<>();
        if (fileContent != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(fileContent)));
            String line;
            int lineNo = 1;
            while ((line = reader.readLine()) != null) {
                for (String pattern : todoPatterns) {
                    int findingIndex = StringUtils.indexOf(line, pattern);
                    while (findingIndex != -1) {
                        Finding finding = new Finding(lineNo, lineNo, findingIndex + 1, findingIndex + pattern.length());
                        findings.add(finding);
                        line = StringUtils.substringAfter(line, pattern);
                        findingIndex = StringUtils.indexOf(line, pattern);
                    }
                }
                lineNo++;
            }
        }
        return findings;
    }

}
