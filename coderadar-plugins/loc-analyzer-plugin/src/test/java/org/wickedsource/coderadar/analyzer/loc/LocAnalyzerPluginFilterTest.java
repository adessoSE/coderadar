package org.wickedsource.coderadar.analyzer.loc;

import io.reflectoring.coderadar.plugin.api.AnalyzerFileFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocAnalyzerPluginFilterTest {

  @Test
  public void filterAcceptsTheCorrectFiles() {
    AnalyzerFileFilter filter = new LocAnalyzerFileFilter();

    Assertions.assertTrue(filter.acceptFilename("Testfile.java"));
    Assertions.assertFalse(filter.acceptFilename("Testfile.txt"));
    Assertions.assertFalse(filter.acceptFilename("Testfile"));
  }
}
