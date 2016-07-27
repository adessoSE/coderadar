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
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValue;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository;
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

    @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
    public ResponseEntity<MetricOutputsResources> queryMetrics(@PathVariable Long projectId, @Valid @RequestBody QueryParams query) {
        projectVerifier.checkProjectExistsOrThrowException(projectId);
        MetricOutputsResources resource = new MetricOutputsResources();

        if (query.scanCommits()) {
            if (query.outputMetrics()) {
                List<MetricValueDTO> commitMetricValues = metricValueRepository.findValuesAggregatedByCommitAndMetric(query.getSubjects().getCommits(), query.getOutputs().getMetrics());
                resource.setCommitsList(commitMetricValues);
            }
        }

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

}
