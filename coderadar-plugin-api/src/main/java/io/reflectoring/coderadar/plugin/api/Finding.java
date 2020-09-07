package io.reflectoring.coderadar.plugin.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * A Finding defines a position within a source code file where a certain metric found an issue.
 */
@EqualsAndHashCode
@Data
@NoArgsConstructor
public class Finding {

    private int lineStart;
    private int lineEnd;
    private int charStart;
    private int charEnd;
    private String message;

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
