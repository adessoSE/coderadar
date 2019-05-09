package io.reflectoring.coderadar.core.vcs.port.driver;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@Data
@AllArgsConstructor
public class CloneRepositoryCommand {
    private String remoteUrl;
    private File localDir;
}
