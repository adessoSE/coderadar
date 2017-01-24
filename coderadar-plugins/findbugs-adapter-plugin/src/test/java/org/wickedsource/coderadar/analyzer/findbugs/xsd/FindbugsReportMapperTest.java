package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.FileSetMetrics;
import org.wickedsource.coderadar.analyzer.api.Metric;

public class FindbugsReportMapperTest extends TestReportAccessor {

    @Test
    public void mapReport() throws Exception {
        byte[] report = getValidReport();
        FindbugsReportMapper mapper = new FindbugsReportMapper();
        FileSetMetrics metrics = mapper.mapReport(report);

        Metric metric1 = new Metric("findbugs:EI_EXPOSE_REP");
        Metric metric2 = new Metric("findbugs:EI_EXPOSE_REP2");

        Assert.assertNotNull(metrics);
        Assert.assertEquals(5, metrics.getFiles().size());
        Assert.assertEquals(Long.valueOf(1), metrics.getFileMetrics("org/wickedsource/coderadar/annotator/api/Commit.java").getMetricCount(metric1));
        Assert.assertEquals(Long.valueOf(1), metrics.getFileMetrics("org/wickedsource/coderadar/annotator/api/Commit.java").getMetricCount(metric2));
        Assert.assertEquals(Integer.valueOf(26), metrics.getFileMetrics("org/wickedsource/coderadar/annotator/api/Commit.java").getFindings(metric1).get(0).getLineStart());
        Assert.assertEquals(Integer.valueOf(26), metrics.getFileMetrics("org/wickedsource/coderadar/annotator/api/Commit.java").getFindings(metric1).get(0).getLineEnd());
    }

}