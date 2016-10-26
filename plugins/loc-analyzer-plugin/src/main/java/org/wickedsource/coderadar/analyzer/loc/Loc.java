package org.wickedsource.coderadar.analyzer.loc;

public class Loc {

    private int loc;

    private int sloc;

    private int cloc;

    private int eloc;

    public void incrementLoc() {
        this.loc++;
    }

    public void incrementSloc() {
        this.sloc++;
    }

    public void incrementCloc() {
        this.cloc++;
    }

    public void incrementEloc() {
        this.eloc++;
    }

    /**
     * Lines of code. Total lines of code, including comments and empty lines.
     */
    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    /**
     * Source lines of code. Total lines of code, excluding comments and empty lines.
     */
    public int getSloc() {
        return sloc;
    }

    public void setSloc(int sloc) {
        this.sloc = sloc;
    }

    /**
     * Comment lines of code. Total number of lines that only contain comments.
     */
    public int getCloc() {
        return cloc;
    }

    public void setCloc(int cloc) {
        this.cloc = cloc;
    }

    /**
     * Effective lines of code. Total lines of code, excluding comments, empty lines and "header" and "footer" lines (e.g. single braces and import statements).
     */
    public int getEloc() {
        return eloc;
    }

    public void setEloc(int eloc) {
        this.eloc = eloc;
    }
}
