package org.wickedsource.coderadar.analyzer.loc;

import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.AnalyzerFileFilter;

public class LocFileAnalyzerPluginFilterTest {

    @Test
    public void filterAcceptsTheCorrectFiles(){
        AnalyzerFileFilter filter = new LocAnalyzerFileFilter();

        Assert.assertFalse(filter.acceptBinary());
        Assert.assertTrue(filter.acceptFilename("Testfile.java"));
        Assert.assertFalse(filter.acceptFilename("Testfile.txt"));
        Assert.assertFalse(filter.acceptFilename("Testfile"));
    }

}