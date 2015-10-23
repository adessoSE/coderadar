package org.wickedsource.coderadar.analyzer.loc;

import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.AnalyzerFilter;

public class LocAnalyzerFilterTest {

    @Test
    public void test(){
        AnalyzerFilter filter = new LocAnalyzerFilter();

        Assert.assertFalse(filter.acceptBinary());
        Assert.assertTrue(filter.acceptFilename("Testfile.java"));
        Assert.assertFalse(filter.acceptFilename("Testfile.txt"));
        Assert.assertFalse(filter.acceptFilename("Testfile"));
    }

}