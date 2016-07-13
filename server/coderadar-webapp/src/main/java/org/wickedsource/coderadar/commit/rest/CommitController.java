package org.wickedsource.coderadar.commit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;

@Controller
@ExposesResourceFor(Commit.class)
@Transactional
@RequestMapping(path = "/projects/{projectId}/commits")
public class CommitController {

    private CommitRepository commitRepository;

    private CommitResourceAssembler commitResourceAssembler;

    @Autowired
    public CommitController(CommitRepository commitRepository, CommitResourceAssembler commitResourceAssembler) {
        this.commitRepository = commitRepository;
        this.commitResourceAssembler = commitResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
    public ResponseEntity<PagedResources<CommitResource>> listCommits(@PageableDefault Pageable pageable, PagedResourcesAssembler pagedResourcesAssembler) {
        Page<Commit> commitsPage = commitRepository.findAll(pageable);
        PagedResources<CommitResource> pagedResources = pagedResourcesAssembler.toResource(commitsPage, commitResourceAssembler);
        return new ResponseEntity<>(pagedResources, HttpStatus.OK);
    }

}
