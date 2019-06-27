package io.reflectoring.coderadar.analyzer.loc.profiles;

import io.reflectoring.coderadar.analyzer.loc.Loc;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

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
