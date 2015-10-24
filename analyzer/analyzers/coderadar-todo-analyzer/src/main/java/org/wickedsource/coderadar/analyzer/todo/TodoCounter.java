package org.wickedsource.coderadar.analyzer.todo;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TodoCounter {

    private List<String> todoPatterns;

    public TodoCounter(List<String> todoPatterns) {
        this.todoPatterns = todoPatterns;
    }

    public int count(byte[] fileContent) throws IOException {
        if(fileContent != null){
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(fileContent)));
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                for (String pattern : todoPatterns) {
                    count += StringUtils.countMatches(line, pattern);
                }
            }
            return count;
        }else{
            return 0;
        }
    }

}
