package org.wickedsource.coderadar;

import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuration class that holds all configuration parameters passed to the Coderadar application at startup.
 */
@Service
public class CoderadarConfiguration {

    private static final String CONFIG_MASTER = "coderadar.master";

    private static final String CONFIG_SLAVE = "coderadar.slave";

    private static final String CONFIG_WORKDIR = "coderadar.workdir";

    private static final String CONFIG_SCAN_INTERVAL_IN_SECONDS = "coderadar.scanIntervalInSeconds";

    /**
     * Returns if the Coderadar application is configured as a master node in a Coderadar cluster.
     * A node can be configured as master and slave simultaneously. However, there must only be a single master
     * in a cluster.
     */
    public boolean isMaster() {
        return Boolean.valueOf(System.getProperty(CONFIG_MASTER));
    }

    /**
     * Returns if the Coderadar application is configured as a slave node in a Coderadar cluster.
     * A node can be configured as master and slave simultaneously. However, there must only be a single master
     * in a cluster.
     */
    public boolean isSlave() {
        return Boolean.valueOf(System.getProperty(CONFIG_SLAVE));
    }

    /**
     * Returns the folder in which Coderadar puts local vcs data.
     */
    public Path getWorkdir() {
        return Paths.get(System.getProperty(CONFIG_WORKDIR));
    }

    public int getScanIntervalInSeconds() {
        String stringValue = System.getProperty(CONFIG_SCAN_INTERVAL_IN_SECONDS);
        if (stringValue == null) {
            return 60;
        } else {
            return Integer.valueOf(stringValue);
        }
    }

    public void setMaster(boolean value) {
        System.setProperty(CONFIG_MASTER, String.valueOf(value));
    }

    public void setSlave(boolean value) {
        System.setProperty(CONFIG_SLAVE, String.valueOf(value));
    }

    public void setWorkdir(Path workdir) {
        System.setProperty(CONFIG_WORKDIR, workdir.toString());
    }


}
