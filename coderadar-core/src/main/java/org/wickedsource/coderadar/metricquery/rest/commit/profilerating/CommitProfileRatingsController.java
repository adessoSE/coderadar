package org.wickedsource.coderadar.metricquery.rest.commit.profilerating;

import java.util.List;
import javax.validation.Valid;
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
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository;
import org.wickedsource.coderadar.metric.domain.metricvalue.ProfileValuePerCommitDTO;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;

@Controller
@ExposesResourceFor(MetricValue.class)
@Transactional
@RequestMapping(path = "/projects/{projectId}/profileratings/perCommit")
public class CommitProfileRatingsController {

  private ProjectVerifier projectVerifier;

  private MetricValueRepository metricValueRepository;

  private CommitRepository commitRepository;

  @Autowired
  public CommitProfileRatingsController(
      ProjectVerifier projectVerifier,
      MetricValueRepository metricValueRepository,
      CommitRepository commitRepository) {
    this.projectVerifier = projectVerifier;
    this.metricValueRepository = metricValueRepository;
    this.commitRepository = commitRepository;
  }

  @RequestMapping(
    method = {RequestMethod.GET, RequestMethod.POST},
    produces = "application/hal+json"
  )
  public ResponseEntity<CommitProfileRatingsOutputResource> queryProfileRatings(
      @PathVariable Long projectId, @Valid @RequestBody CommitProfileRatingsQuery query) {
    projectVerifier.checkProjectExistsOrThrowException(projectId);

    Commit commit = commitRepository.findByName(query.getCommit());
    if (commit == null) {
      throw new ResourceNotFoundException();
    }

    CommitProfileRatingsOutputResource resource = new CommitProfileRatingsOutputResource();
    List<ProfileValuePerCommitDTO> profileValues =
        metricValueRepository.findValuesAggregatedByCommitAndProfile(
            projectId, commit.getSequenceNumber(), query.getProfiles());
    resource.addProfileValues(profileValues);
    resource.addAbsentProfiles(query.getProfiles());

    return new ResponseEntity<>(resource, HttpStatus.OK);
  }
}
