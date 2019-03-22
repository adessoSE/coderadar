package org.wickedsource.coderadar.qualityprofile.rest;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.core.common.ResourceNotFoundException;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;
import org.wickedsource.coderadar.projectadministration.domain.Project;
import org.wickedsource.coderadar.projectadministration.domain.QualityProfile;
import org.wickedsource.coderadar.qualityprofile.domain.QualityProfileRepository;

@Controller
@Transactional
@RequestMapping(path = "/projects/{projectId}/qualityprofiles")
public class QualityProfileController {

  private ProjectVerifier projectVerifier;

  private QualityProfileRepository qualityProfileRepository;

  @Autowired
  public QualityProfileController(
      ProjectVerifier projectVerifier, QualityProfileRepository qualityProfileRepository) {
    this.projectVerifier = projectVerifier;
    this.qualityProfileRepository = qualityProfileRepository;
  }

  @RequestMapping(method = RequestMethod.POST, produces = "application/hal+json")
  public ResponseEntity<QualityProfileResource> createQualityProfile(
      @RequestBody @Valid QualityProfileResource qualityProfileResource,
      @PathVariable Long projectId) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    QualityProfileResourceAssembler assembler = new QualityProfileResourceAssembler(project);
    QualityProfile profile = assembler.updateEntity(qualityProfileResource, new QualityProfile());
    profile = qualityProfileRepository.save(profile);
    return new ResponseEntity<>(assembler.toResource(profile), HttpStatus.CREATED);
  }

  @RequestMapping(
    path = "/{profileId}",
    method = RequestMethod.POST,
    produces = "application/hal+json"
  )
  public ResponseEntity<QualityProfileResource> updateQualityProfile(
      @Valid @RequestBody QualityProfileResource qualityProfileResource,
      @PathVariable Long profileId,
      @PathVariable Long projectId) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    QualityProfileResourceAssembler assembler = new QualityProfileResourceAssembler(project);
    Optional<QualityProfile> profile = qualityProfileRepository.findById(profileId);
    if (!profile.isPresent()) {
      throw new ResourceNotFoundException();
    }
    QualityProfile qualityProfile = assembler.updateEntity(qualityProfileResource, profile.get());
    qualityProfile = qualityProfileRepository.save(qualityProfile);
    return new ResponseEntity<>(assembler.toResource(qualityProfile), HttpStatus.OK);
  }

  @RequestMapping(
    path = "/{profileId}",
    method = RequestMethod.GET,
    produces = "application/hal+json"
  )
  public ResponseEntity<QualityProfileResource> getQualityProfile(
      @PathVariable Long profileId, @PathVariable Long projectId) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    QualityProfileResourceAssembler assembler = new QualityProfileResourceAssembler(project);
    Optional<QualityProfile> profile = qualityProfileRepository.findById(profileId);
    if (!profile.isPresent()) {
      throw new ResourceNotFoundException();
    }
    return new ResponseEntity<>(assembler.toResource(profile.get()), HttpStatus.OK);
  }

  @RequestMapping(
    path = "/{profileId}",
    method = RequestMethod.DELETE,
    produces = "application/hal+json"
  )
  public ResponseEntity<String> deleteQualityProfile(
      @PathVariable Long profileId, @PathVariable Long projectId) {
    projectVerifier.checkProjectExistsOrThrowException(projectId);
    qualityProfileRepository.deleteById(profileId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
  public ResponseEntity<List<QualityProfileResource>> listQualityProfiles(
      @PathVariable long projectId) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    List<QualityProfile> qualityProfiles = qualityProfileRepository.findByProjectId(projectId);
    QualityProfileResourceAssembler assembler = new QualityProfileResourceAssembler(project);
    List<QualityProfileResource> profileResources = assembler.toResourceList(qualityProfiles);
    return new ResponseEntity<>(profileResources, HttpStatus.OK);
  }
}
