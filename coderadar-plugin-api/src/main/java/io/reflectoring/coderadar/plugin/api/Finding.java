package io.reflectoring.coderadar.plugin.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * A Finding defines a position within a source code file where a certain metric found an issue.
 */
@EqualsAndHashCode
@Getter
public class Finding {

    private final Integer lineStart;

    private final Integer lineEnd;

    private final Integer charStart;

    private final Integer charEnd;

    private final String message;

    /**
     * Marks a finding starting at some point in one line and ending in some point in another line.
     *
     * @param lineStart the line number where the finding starts.
     * @param lineEnd   the line number where the finding ends.
     * @param charStart the position of the character where the finding starts, starting with 1 for
     *                  the first character of the line.
     * @param charEnd   the position of the character where the finding ends, starting with 1 for the
     *                  first character of the line.
     * @param message   the message of the metric.
     */
    public Finding(Integer lineStart, Integer lineEnd, Integer charStart, Integer charEnd, String message) {
        this.lineStart = lineStart;
        this.lineEnd = lineEnd;
        this.charStart = charStart;
        this.charEnd = charEnd;
        this.message = message;
    }
}
