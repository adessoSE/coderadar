package org.wickedsource.coderadar.job.scan.file;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class HasherTest {

  @Test
  public void test() {
    byte[] bytes = "123".getBytes();
    Hasher hasher = new Hasher();
    assertThat(hasher.hash(bytes))
        .isEqualToIgnoringCase("40bd001563085fc35165329ea1ff5c5ecbdbbeef");
  }
}
