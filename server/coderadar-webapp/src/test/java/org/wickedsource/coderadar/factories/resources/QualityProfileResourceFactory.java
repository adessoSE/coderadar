package org.wickedsource.coderadar.factories.resources;

import org.wickedsource.coderadar.metric.domain.qualityprofile.MetricType;
import org.wickedsource.coderadar.metric.rest.qualityprofile.MetricDTO;
import org.wickedsource.coderadar.metric.rest.qualityprofile.QualityProfileResource;

public class QualityProfileResourceFactory {

    public static QualityProfileResource profile() {
        QualityProfileResource resource = new QualityProfileResource();
        resource.setProfileName("Quality Profile");
        resource.addMetric(new MetricDTO("dummyMetric1", MetricType.VIOLATION));
        resource.addMetric(new MetricDTO("dummyMetric2", MetricType.COMPLIANCE));
        return resource;
    }

    public static QualityProfileResource profile2() {
        QualityProfileResource resource = new QualityProfileResource();
        resource.setProfileName("Quality Profile (updated)");
        resource.addMetric(new MetricDTO("dummyMetric3", MetricType.VIOLATION));
        resource.addMetric(new MetricDTO("dummyMetric4", MetricType.COMPLIANCE));
        return resource;
    }

}
