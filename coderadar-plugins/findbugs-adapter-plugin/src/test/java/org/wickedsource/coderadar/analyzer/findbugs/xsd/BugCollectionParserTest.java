package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import org.junit.Assert;
import org.junit.Test;

public class BugCollectionParserTest extends TestReportAccessor {

    @Test
    public void parsesSuccessfully() throws Exception {
        byte[] report = getValidReport();

        BugCollectionParser parser = new BugCollectionParser();
        BugCollection collection = parser.fromBytes(report);

        Assert.assertNotNull(collection);
    }

}