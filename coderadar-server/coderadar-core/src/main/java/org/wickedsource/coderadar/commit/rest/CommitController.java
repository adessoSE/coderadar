package org.wickedsource.coderadar.commit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;

@Controller
@Transactional
@RequestMapping(path = "/projects/{projectId}/commits")
public class CommitController {

  private CommitRepository commitRepository;

  private CommitResourceAssembler commitResourceAssembler = new CommitResourceAssembler();

  @Autowired
  public CommitController(CommitRepository commitRepository) {
    this.commitRepository = commitRepository;
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
  public ResponseEntity<Page<CommitResource>> listCommits(
      @PathVariable long projectId, Pageable pageable) {
    Page<Commit> commits = commitRepository.findByProjectId(projectId, pageable);
    Page<CommitResource> commitResources =
        new PageImpl<>(
            commitResourceAssembler.toResourceList(commits), pageable, commits.getTotalElements());
    return new ResponseEntity<>(commitResources, HttpStatus.OK);
  }
}
