package io.reflectoring.coderadar.plugin.api;

/**
 * A Finding defines a position within a source code file where a certain metric found an issue.
 */
public class Finding {

    private Integer lineStart = null;

    private Integer lineEnd = null;

    private Integer charStart = null;

    private Integer charEnd = null;

    /**
     * Marks a finding over multiple complete lines.
     *
     * @param lineStart the line number where the finding starts.
     * @param lineEnd   the line number where the finding ends.
     */
    public Finding(Integer lineStart, Integer lineEnd) {
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
     */
    public Finding(Integer lineStart, Integer charStart, Integer charEnd) {
        this.lineStart = lineStart;
        this.charStart = charStart;
        this.charEnd = charEnd;
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
     */
    public Finding(Integer lineStart, Integer lineEnd, Integer charStart, Integer charEnd) {
        this.lineStart = lineStart;
        this.lineEnd = lineEnd;
        this.charStart = charStart;
        this.charEnd = charEnd;
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
}
