package io.reflectoring.coderadar.analyzer.loc.profiles;

import static org.assertj.core.api.Assertions.assertThat;

import io.reflectoring.coderadar.analyzer.loc.LinesOfCode;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class JavaLocProfileTest extends AbstractLocProfileTest {

  private final LocProfile profile = new JavaLocProfile();

  @Test
  void standard() throws IOException {
    LinesOfCode loc = countLines("/testcode/java/standard.java.loctest", profile);
    assertThat(loc.getLoc()).isEqualTo(7);
    assertThat(loc.getSloc()).isEqualTo(3);
    assertThat(loc.getEloc()).isEqualTo(2);
    assertThat(loc.getCloc()).isEqualTo(4);
  }

  @Test
  void nested() throws IOException {
    LinesOfCode loc = countLines("/testcode/java/nested.java.loctest", profile);
    assertThat(loc.getLoc()).isEqualTo(12);
    assertThat(loc.getSloc()).isEqualTo(5);
    assertThat(loc.getEloc()).isEqualTo(3);
    assertThat(loc.getCloc()).isEqualTo(5);
  }
}
