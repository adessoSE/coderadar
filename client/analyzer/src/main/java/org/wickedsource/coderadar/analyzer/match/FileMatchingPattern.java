package org.wickedsource.coderadar.analyzer.match;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class allows to specify a set of include patterns and exclude patterns that define a set of files from
 * a given starting folder. The patterns are Ant-style patterns. The exclude patterns are applied AFTER of
 * the include patterns so that excludes can be used to override certain includes.
 * <p>
 * <h3>Examples</h3>
 * <ul>
 * <li>{@code com/t?st.jsp} &mdash; matches {@code com/test.jsp} but also
 * {@code com/tast.jsp} or {@code com/txst.jsp}</li>
 * <li>{@code com/*.jsp} &mdash; matches all {@code .jsp} files in the
 * {@code com} directory</li>
 * <li><code>com/&#42;&#42;/test.jsp</code> &mdash; matches all {@code test.jsp}
 * files underneath the {@code com} path</li>
 * <li><code>org/springframework/&#42;&#42;/*.jsp</code> &mdash; matches all
 * {@code .jsp} files underneath the {@code org/springframework} path</li>
 * <li><code>org/&#42;&#42;/servlet/bla.jsp</code> &mdash; matches
 * {@code org/springframework/servlet/bla.jsp} but also
 * {@code org/springframework/testing/servlet/bla.jsp} and {@code org/servlet/bla.jsp}</li>
 * </ul>
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
