package org.wickedsource.coderadar.analyzer.rest;

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
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfiguration;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfigurationRepository;
import org.wickedsource.coderadar.core.rest.validation.ResourceNotFoundException;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;

import javax.validation.Valid;
import java.util.List;

@Controller
@ExposesResourceFor(AnalyzerConfiguration.class)
@Transactional
@RequestMapping(path = "/projects/{projectId}/analyzers")
public class AnalyzerConfigurationController {

    private AnalyzerConfigurationRepository analyzerConfigurationRepository;

    private ProjectVerifier projectVerifier;

    private AnalyzerVerifier analyzerVerifier;

    @Autowired
    public AnalyzerConfigurationController(AnalyzerConfigurationRepository analyzerConfigurationRepository, ProjectVerifier projectVerifier, AnalyzerVerifier analyzerVerifier) {
        this.analyzerConfigurationRepository = analyzerConfigurationRepository;
        this.projectVerifier = projectVerifier;
        this.analyzerVerifier = analyzerVerifier;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AnalyzerConfigurationResource> addAnalyzerConfigurationToProject(@PathVariable Long projectId, @Valid @RequestBody AnalyzerConfigurationResource resource) {
        analyzerVerifier.checkAnalyzerExistsOrThrowException(resource.getAnalyzerName());
        AnalyzerConfigurationResourceAssembler assembler = new AnalyzerConfigurationResourceAssembler(projectId);
        Project project = projectVerifier.loadProjectOrThrowException(projectId);
        AnalyzerConfiguration entity = assembler.toEntity(resource, project);
        AnalyzerConfiguration savedEntity = analyzerConfigurationRepository.save(entity);
        return new ResponseEntity<>(assembler.toResource(savedEntity), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAnalyzerConfigurationFromProject(@PathVariable Long projectId, @Valid @RequestBody String analyzerName) {
        analyzerConfigurationRepository.deleteByProjectIdAndAnalyzerName(projectId, analyzerName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AnalyzerConfigurationResource>> getAnalyzerConfigurationsForProject(@PathVariable Long projectId) {
        projectVerifier.checkProjectExistsOrThrowException(projectId);
        List<AnalyzerConfiguration> configurations = analyzerConfigurationRepository.findByProjectId(projectId);
        AnalyzerConfigurationResourceAssembler assembler = new AnalyzerConfigurationResourceAssembler(projectId);
        return new ResponseEntity<>(assembler.toResourceList(configurations), HttpStatus.OK);
    }

    @RequestMapping(path="/{analyzerConfigurationId}", method = RequestMethod.GET)
    public ResponseEntity<AnalyzerConfigurationResource> getSingleAnalyzerConfigurationForProject(@PathVariable Long projectId, @PathVariable Long analyzerConfigurationId){
        projectVerifier.checkProjectExistsOrThrowException(projectId);
        AnalyzerConfiguration configuration = analyzerConfigurationRepository.findByProjectIdAndId(projectId, analyzerConfigurationId);
        if(configuration == null){
            throw new ResourceNotFoundException();
        }
        AnalyzerConfigurationResourceAssembler assembler = new AnalyzerConfigurationResourceAssembler(projectId);
        return new ResponseEntity<>(assembler.toResource(configuration), HttpStatus.OK);
    }

    @RequestMapping(path="/{analyzerConfigurationId}", method = RequestMethod.POST)
    public ResponseEntity<AnalyzerConfigurationResource> updateAnalyzerConfiguration(@PathVariable Long projectId, @PathVariable Long analyzerConfigurationId, @RequestBody @Valid AnalyzerConfigurationResource resource){
        analyzerVerifier.checkAnalyzerExistsOrThrowException(resource.getAnalyzerName());
        AnalyzerConfigurationResourceAssembler assembler = new AnalyzerConfigurationResourceAssembler(projectId);
        Project project = projectVerifier.loadProjectOrThrowException(projectId);
        AnalyzerConfiguration entity = analyzerConfigurationRepository.findByProjectIdAndId(projectId, analyzerConfigurationId);
        AnalyzerConfiguration savedEntity = assembler.updateEntity(entity, resource, project);
        return new ResponseEntity<>(assembler.toResource(savedEntity), HttpStatus.OK);
    }



}
