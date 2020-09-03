package io.reflectoring.coderadar.analyzer.loc.profiles;

import static org.assertj.core.api.Assertions.assertThat;

import io.reflectoring.coderadar.analyzer.loc.LinesOfCode;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class XmlLinesOfCodeProfileTest extends AbstractLocProfileTest {

  private final LocProfile profile = new XmlLocProfile();

  @Test
  void standard() throws IOException {
    LinesOfCode loc = countLines("/testcode/xml/standard.xml.loctest", profile);
    assertThat(loc.getLoc()).isEqualTo(15);
    assertThat(loc.getSloc()).isEqualTo(10);
    assertThat(loc.getEloc()).isEqualTo(10);
    assertThat(loc.getCloc()).isEqualTo(2);
  }

  @Test
  void nested() throws IOException {
    LinesOfCode loc = countLines("/testcode/xml/nested.xml.loctest", profile);
    assertThat(loc.getLoc()).isEqualTo(15);
    assertThat(loc.getSloc()).isEqualTo(9);
    assertThat(loc.getEloc()).isEqualTo(9);
    assertThat(loc.getCloc()).isEqualTo(4);
  }
}
