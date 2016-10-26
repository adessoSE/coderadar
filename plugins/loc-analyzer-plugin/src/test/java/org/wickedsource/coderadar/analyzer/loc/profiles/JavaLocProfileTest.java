package org.wickedsource.coderadar.analyzer.loc.profiles;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.loc.Loc;
import org.wickedsource.coderadar.analyzer.loc.LocCounter;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaLocProfileTest {

    private JavaLocProfile profile = new JavaLocProfile();

    @Test
    public void standard() throws IOException {
        Loc loc = countLines("/testcode/java/standard.txt");
        assertThat(loc.getLoc()).isEqualTo(7);
        assertThat(loc.getSloc()).isEqualTo(3);
        assertThat(loc.getEloc()).isEqualTo(2);
        assertThat(loc.getCloc()).isEqualTo(4);
    }

    @Test
    public void nested() throws IOException {
        Loc loc = countLines("/testcode/java/nested.txt");
        assertThat(loc.getLoc()).isEqualTo(12);
        assertThat(loc.getSloc()).isEqualTo(5);
        assertThat(loc.getEloc()).isEqualTo(3);
        assertThat(loc.getCloc()).isEqualTo(5);
    }

    private Loc countLines(String path) throws IOException {
        InputStream in = getClass().getResourceAsStream(path);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(in, out);
        byte[] file = out.toByteArray();
        LocCounter counter = new LocCounter();
        return counter.count(file, profile);
    }

}