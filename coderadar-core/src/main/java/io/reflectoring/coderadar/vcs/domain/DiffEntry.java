package io.reflectoring.coderadar.vcs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiffEntry {
    private String oldPath;
    private String newPath;
    private int changeType;
}
