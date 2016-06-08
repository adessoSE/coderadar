package org.wickedsource.coderadar.analyzer.match;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class allows to specify a set of include patterns and exclude patterns that define a set of files from
 * a given starting folder. The patterns are Ant-style patterns. The exclude patterns are applied AFTER
 * the include patterns so that excludes can be used to override certain includes.
 */
public class FileMatchingPattern {

    private Path startFolder;

    private List<String> includePatterns = new ArrayList<>();

    private List<String> excludePatterns = new ArrayList<>();

    /**
     * Constructs a new FileMatchingPattern.
     *
     * @param startFolder the folder that is the starting point from which the include and exclude patterns are
     *                    applied.
     */
    public FileMatchingPattern(Path startFolder) {
        this.startFolder = startFolder;
    }

    public void addIncludePattern(String pattern) {
        this.includePatterns.add(pattern);
    }

    public void addExcludePattern(String pattern) {
        this.excludePatterns.add(pattern);
    }

    public List<String> getIncludePatterns() {
        return Collections.unmodifiableList(includePatterns);
    }

    public List<String> getExcludePatterns() {
        return Collections.unmodifiableList(excludePatterns);
    }

    public Path getStartFolder() {
        return startFolder;
    }
}
