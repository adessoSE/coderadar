package org.wickedsource.coderadar.metricquery.rest.module;

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
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.core.rest.validation.ResourceNotFoundException;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValue;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValuePerModuleDTO;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;

import javax.validation.Valid;
import java.util.List;

@Controller
@ExposesResourceFor(MetricValue.class)
@Transactional
@RequestMapping(path = "/projects/{projectId}/metricvalues")
public class ModuleMetricsController {

    private ProjectVerifier projectVerifier;

    private MetricValueRepository metricValueRepository;

    private CommitRepository commitRepository;

    @Autowired
    public ModuleMetricsController(ProjectVerifier projectVerifier, MetricValueRepository metricValueRepository, CommitRepository commitRepository) {
        this.projectVerifier = projectVerifier;
        this.metricValueRepository = metricValueRepository;
        this.commitRepository = commitRepository;
    }

    @RequestMapping(path = "/perModule", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/hal+json")
    public ResponseEntity<ModuleTreeResource> queryMetrics(@PathVariable Long projectId, @Valid @RequestBody ModuleMetricsQuery query) {
        projectVerifier.checkProjectExistsOrThrowException(projectId);
        Commit commit = commitRepository.findByName(query.getCommit());
        if (commit == null) {
            throw new ResourceNotFoundException();
        }
        List<MetricValuePerModuleDTO> moduleValues = metricValueRepository.findValuesAggregatedByModule(projectId, commit.getSequenceNumber(), query.getMetrics());
        ModuleMetricsTreeResourceAssembler assembler = new ModuleMetricsTreeResourceAssembler();
        ModuleTreeResource moduleTreeResource = assembler.toResource(moduleValues);
        return new ResponseEntity<>(moduleTreeResource, HttpStatus.OK);
    }

}