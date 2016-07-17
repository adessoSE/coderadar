package org.wickedsource.coderadar.analyzingstrategy.rest;

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
import org.wickedsource.coderadar.analyzingstrategy.domain.AnalyzingStrategy;
import org.wickedsource.coderadar.analyzingstrategy.domain.AnalyzingStrategyRepository;
import org.wickedsource.coderadar.analyzingstrategy.domain.ProjectResetException;
import org.wickedsource.coderadar.analyzingstrategy.domain.ProjectResetter;
import org.wickedsource.coderadar.core.rest.validation.ResourceNotFoundException;
import org.wickedsource.coderadar.core.rest.validation.UserException;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;

import javax.validation.Valid;

@Controller
@ExposesResourceFor(Project.class)
@Transactional
@RequestMapping(path = "/projects/{projectId}/strategy")
public class AnalyzingStrategyController {

    private ProjectVerifier projectVerifier;

    private AnalyzingStrategyRepository analyzingStrategyRepository;

    private ProjectResetter projectResetter;

    @Autowired
    public AnalyzingStrategyController(ProjectVerifier projectVerifier, AnalyzingStrategyRepository analyzingStrategyRepository, ProjectResetter projectResetter) {
        this.projectVerifier = projectVerifier;
        this.analyzingStrategyRepository = analyzingStrategyRepository;
        this.projectResetter = projectResetter;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<AnalyzingStrategyResource> getAnalyzingStrategy(@PathVariable("projectId") Long projectId) {
        projectVerifier.checkProjectExistsOrThrowException(projectId);
        AnalyzingStrategy strategy = analyzingStrategyRepository.findByProjectId(projectId);
        if(strategy == null){
            throw new ResourceNotFoundException();
        }
        AnalyzingStrategyResourceAssembler assembler = new AnalyzingStrategyResourceAssembler(projectId);
        return new ResponseEntity<>(assembler.toResource(strategy), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AnalyzingStrategyResource> setAnalyzingStrategy(@PathVariable("projectId") Long projectId, @Valid @RequestBody AnalyzingStrategyResource resource) {
        Project project = projectVerifier.loadProjectOrThrowException(projectId);

        if(resource.isRescanNullsafe()){
            try {
                projectResetter.resetProject(project);
            }catch(ProjectResetException e){
                throw new UserException("Cannot re-scan project while there are analyzer jobs waiting or running.");
            }
        }

        AnalyzingStrategy strategy = analyzingStrategyRepository.findByProjectId(projectId);
        if(strategy == null){
            strategy = new AnalyzingStrategy();
        }

        AnalyzingStrategyResourceAssembler assembler = new AnalyzingStrategyResourceAssembler(projectId);
        strategy = assembler.updateEntity(strategy, resource, project);
        analyzingStrategyRepository.save(strategy);
        return new ResponseEntity<>(assembler.toResource(strategy), HttpStatus.OK);
    }

}
