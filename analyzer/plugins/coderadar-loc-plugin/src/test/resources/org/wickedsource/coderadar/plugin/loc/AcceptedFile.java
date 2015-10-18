package org.wickedsource.coderadar.plugin.loc;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestFile {

    public int count(byte[] fileContent) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(fileContent)));
        String line;
        int count = 0;
        while ((line = reader.readLine()) != null) {
            count++;
        }
        return count;
    }

}
