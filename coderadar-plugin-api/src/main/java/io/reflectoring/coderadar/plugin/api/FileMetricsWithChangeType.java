package io.reflectoring.coderadar.plugin.api;

public class FileMetricsWithChangeType extends FileMetrics {

    private ChangeType changeType;

    public FileMetricsWithChangeType() {
    }

    public FileMetricsWithChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public FileMetricsWithChangeType(FileMetrics copyFrom, ChangeType changeType) {
        super(copyFrom);
        this.changeType = changeType;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FileMetricsWithChangeType that = (FileMetricsWithChangeType) o;

        if (changeType != that.changeType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (changeType != null ? changeType.hashCode() : 0);
        return result;
    }
}
