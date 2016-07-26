package org.wickedsource.coderadar.metric.rest.metric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;

@Controller
@Transactional
@RequestMapping(path = "/projects/{projectId}/metrics")
public class MetricsController {

    private ProjectVerifier projectVerifier;

    private MetricValueRepository metricValueRepository;

    @Autowired
    public MetricsController(ProjectVerifier projectVerifier, MetricValueRepository metricValueRepository) {
        this.projectVerifier = projectVerifier;
        this.metricValueRepository = metricValueRepository;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = {RequestMethod.GET}, produces = "application/hal+json")
    public ResponseEntity<PagedResources<MetricResource>> listMetrics(@PathVariable Long projectId, @PageableDefault Pageable pageable, PagedResourcesAssembler pagedResourcesAssembler) {
        projectVerifier.checkProjectExistsOrThrowException(projectId);
        Page<String> metricsPage = metricValueRepository.findMetricsInProject(projectId, pageable);
        MetricResourceAssembler assembler = new MetricResourceAssembler();
        PagedResources<MetricResource> pagedResources = pagedResourcesAssembler.toResource(metricsPage, assembler);
        return new ResponseEntity<>(pagedResources, HttpStatus.OK);
    }

}
