package org.wickedsource.coderadar.metric.rest.metricvalue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.metric.domain.MetricValue;
import org.wickedsource.coderadar.metric.domain.MetricValueDTO;
import org.wickedsource.coderadar.metric.domain.MetricValueRepository;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;

import javax.validation.Valid;
import java.util.List;

@Controller
@ExposesResourceFor(MetricValue.class)
@Transactional
@RequestMapping(path = "/projects/{projectId}/metricvalues")
public class MetricValuesController {

    private ProjectVerifier projectVerifier;

    private MetricValueRepository metricValueRepository;

    @Autowired
    public MetricValuesController(ProjectVerifier projectVerifier, MetricValueRepository metricValueRepository) {
        this.projectVerifier = projectVerifier;
        this.metricValueRepository = metricValueRepository;
    }

    @RequestMapping(path = "/perCommit", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/hal+json")
    public ResponseEntity<CommitMetricsResource> listCommitMetrics(@PathVariable Long projectId, @Valid @RequestBody QueryParams query) {
        projectVerifier.checkProjectExistsOrThrowException(projectId);
        List<MetricValueDTO> metricValues = metricValueRepository.findValuesAggregatedByCommitAndMetric(query.getCommitNames(), query.getMetricNames());
        CommitMetricsResource resource = new CommitMetricsResource(metricValues);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

}
