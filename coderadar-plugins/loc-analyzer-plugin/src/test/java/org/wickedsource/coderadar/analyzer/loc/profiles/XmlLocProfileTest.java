package org.wickedsource.coderadar.analyzer.loc.profiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.loc.Loc;

public class XmlLocProfileTest extends AbstractLocProfileTest {

  private LocProfile profile = new XmlLocProfile();

  @Test
  public void standard() throws IOException {
    Loc loc = countLines("/testcode/xml/standard.xml.loctest", profile);
    assertThat(loc.getLoc()).isEqualTo(15);
    assertThat(loc.getSloc()).isEqualTo(10);
    assertThat(loc.getEloc()).isEqualTo(10);
    assertThat(loc.getCloc()).isEqualTo(2);
  }

  @Test
  public void nested() throws IOException {
    Loc loc = countLines("/testcode/xml/nested.xml.loctest", profile);
    assertThat(loc.getLoc()).isEqualTo(15);
    assertThat(loc.getSloc()).isEqualTo(9);
    assertThat(loc.getEloc()).isEqualTo(9);
    assertThat(loc.getCloc()).isEqualTo(4);
  }
}
