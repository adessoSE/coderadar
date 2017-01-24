package org.wickedsource.coderadar.analyzer.loc.profiles;

import java.util.HashMap;
import java.util.Map;

public class Profiles {

    private static final Map<String, LocProfile> PROFILES = new HashMap<>();

    static {
        PROFILES.put(".java", new JavaLocProfile());
        PROFILES.put(".xml", new XmlLocProfile());
    }

    public static LocProfile getForFile(String file) {
        int lastDotIndex = file.lastIndexOf('.');

        if (lastDotIndex == -1) {
            return null;
        }

        String fileEnding = file.substring(lastDotIndex);
        return PROFILES.get(fileEnding);
    }

}
