package org.wickedsource.coderadar.vcs.git;

import org.eclipse.jgit.diff.DiffEntry;
import org.wickedsource.coderadar.analyzer.api.ChangeType;

public class ChangeTypeMapper {

    public ChangeType jgitToCoderadar(DiffEntry.ChangeType changeType) {
        return ChangeType.valueOf(changeType.name());
    }

    public DiffEntry.ChangeType coderadarToJgit(ChangeType changeType) {
        return DiffEntry.ChangeType.valueOf(changeType.name());
    }
}