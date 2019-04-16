package io.reflectoring.coderadar.core.analyzer.port.driver;

import java.util.Date;

public class StartAnalyzingCommand {
    Long projectId;
    Date from;
    Boolean rescan;
}
