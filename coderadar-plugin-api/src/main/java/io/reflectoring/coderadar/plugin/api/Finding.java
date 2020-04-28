package io.reflectoring.coderadar.plugin.api;

import lombok.EqualsAndHashCode;

/**
 * A Finding defines a position within a source code file where a certain metric found an issue.
 */
@EqualsAndHashCode
public class Finding {

    private Integer lineStart = null;

    private Integer lineEnd = null;

    private Integer charStart = null;

    private Integer charEnd = null;

    private String message = null;

    /**
     * Marks a finding over multiple complete lines.
     *
     * @param lineStart the line number where the finding starts.
     * @param lineEnd   the line number where the finding ends.
     * @param message the message of the metric.
     */
    public Finding(Integer lineStart, Integer lineEnd, String message) {
        this.lineStart = lineStart;
        this.lineEnd = lineEnd;
    }

    /**
     * Marks a finding over part of a single line.
     *
     * @param lineStart the line number.
     * @param charStart the position of the character where the finding starts, starting with 1 for
     *                  the first character of the line.
     * @param charEnd   the position of the character where the finding ends, starting with 1 for the
     *                  first character of the line.
     * @param message the message of the metric.
     */
    public Finding(Integer lineStart, Integer charStart, Integer charEnd, String message) {
        this.lineStart = lineStart;
        this.charStart = charStart;
        this.charEnd = charEnd;
        this.message = message;
    }

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

    public Integer getLineStart() {
        return lineStart;
    }

    public Integer getLineEnd() {
        return lineEnd;
    }

    public Integer getCharStart() {
        return charStart;
    }

    public Integer getCharEnd() {
        return charEnd;
    }

    public String getMessage() {
        return message;
    }
}
