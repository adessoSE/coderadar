package org.wickedsource.coderadar.analyzer.loc.profiles;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.wickedsource.coderadar.analyzer.loc.Loc;
import org.wickedsource.coderadar.analyzer.loc.LocAnalyzerPlugin;
import org.wickedsource.coderadar.analyzer.loc.LocCounter;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Java6Assertions.assertThat;

public abstract class AbstractLocProfileTest {

    Loc countLines(String path, LocProfile profile) throws IOException {
        InputStream in = getClass().getResourceAsStream(path);
        assertThat(in).isNotNull();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(in, out);
        byte[] file = out.toByteArray();
        LocCounter counter = new LocCounter();

        path = path.replaceAll("\\.loctest", "");
        assertThatProfileIsRegistered(path, file, profile);

        return counter.count(file, profile);
    }

    private void assertThatProfileIsRegistered(String path, byte[] file, LocProfile profile) {
        LocAnalyzerPlugin plugin = new LocAnalyzerPlugin();

        assertThat(Profiles.getForFile(path))
                .as(String.format("LocProfile for file '%s' is not registered!", path))
                .isNotNull();

        assertThat(plugin.analyzeFile(path, file))
                .as(String.format("%s returns null metric object for file '%s'!", LocAnalyzerPlugin.class.getSimpleName(), path))
                .isNotNull();
    }
}
