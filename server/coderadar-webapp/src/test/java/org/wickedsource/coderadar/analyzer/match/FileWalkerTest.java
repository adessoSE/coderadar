package org.wickedsource.coderadar.analyzer.match;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileWalkerTest {

    @Test
    public void testWalk() throws IOException {
        List<String> walkedFiles = new ArrayList<>();
        FileWalker walker = new FileWalker() {
            @Override
            public void processFile(Path file) {
                walkedFiles.add(file.getFileName().toString());
            }
        };

        FileMatchingPattern pattern = new FileMatchingPattern(Paths.get("src/test/FileWalkerTest"));
        pattern.addIncludePattern("folder1/**/file*.txt");
        pattern.addExcludePattern("folder1/folder12/*ignore.txt");
        walker.walk(pattern);

        Assert.assertTrue(walkedFiles.contains("file111.txt"));
        Assert.assertTrue(walkedFiles.contains("file121.txt"));
        Assert.assertTrue(walkedFiles.contains("file11.txt"));
        Assert.assertFalse(walkedFiles.contains("ignore.txt"));
        Assert.assertFalse(walkedFiles.contains("file122-ignore.txt"));
    }

}