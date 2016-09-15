package org.wickedsource.coderadar.core.configuration;

import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuration class that holds all configuration parameters passed to the Coderadar application at startup.
 */
@Configuration
public class CoderadarConfiguration {

    public static final int TIMER_INTERVAL = 100;

    public static final String MASTER = "coderadar.master";

    public static final String SLAVE = "coderadar.slave";

    public static final String WORKDIR = "coderadar.workdir";

    public static final String SCAN_INTERVAL_IN_SECONDS = "coderadar.scanIntervalInSeconds";

    /**
     * Returns if the Coderadar application is configured as a master node in a Coderadar cluster.
     * A node can be configured as master and slave simultaneously. However, there must only be a single master
     * in a cluster.
     */
    public boolean isMaster() {
        return Boolean.valueOf(System.getProperty(MASTER));
    }

    /**
     * Returns if the Coderadar application is configured as a slave node in a Coderadar cluster.
     * A node can be configured as master and slave simultaneously. However, there must only be a single master
     * in a cluster.
     */
    public boolean isSlave() {
        return Boolean.valueOf(System.getProperty(SLAVE));
    }

    /**
     * Returns the folder in which Coderadar puts local vcs data.
     */
    public Path getWorkdir() {
        String path = System.getProperty(WORKDIR);
        return Paths.get(path);
    }

    public int getScanIntervalInSeconds() {
        String stringValue = System.getProperty(SCAN_INTERVAL_IN_SECONDS);
        if (stringValue == null) {
            return 60;
        } else {
            return Integer.valueOf(stringValue);
        }
    }

    public void setMaster(boolean value) {
        System.setProperty(MASTER, String.valueOf(value));
    }

    public void setSlave(boolean value) {
        System.setProperty(SLAVE, String.valueOf(value));
    }

    public void setWorkdir(Path workdir) {
        System.setProperty(WORKDIR, workdir.toString());
    }

}
