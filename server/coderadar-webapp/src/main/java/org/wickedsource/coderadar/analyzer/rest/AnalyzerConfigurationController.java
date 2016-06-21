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
import org.wickedsource.coderadar.analyzer.api.SourceCodeFileAnalyzerPlugin;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfiguration;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfigurationRepository;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerPluginRegistry;
import org.wickedsource.coderadar.core.rest.validation.ValidationException;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@ExposesResourceFor(AnalyzerConfiguration.class)
@Transactional
@RequestMapping(path = "/projects/{projectId}/analyzers")
public class AnalyzerConfigurationController {

    @Autowired
    private AnalyzerConfigurationRepository analyzerConfigurationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AnalyzerPluginRegistry analyzerRegistry;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AnalyzerConfigurationResource> setAnalyzerConfigurationForProject(@PathVariable Long projectId, @Valid @RequestBody AnalyzerConfigurationResource resource) {
        checkAnalyzerExistsOrThrowException(resource.getAnalyzerName());
        AnalyzerConfigurationResourceAssembler assembler = new AnalyzerConfigurationResourceAssembler(projectId);
        Project project = loadProjectOrThrowException(projectId);
        AnalyzerConfiguration entity = analyzerConfigurationRepository.findByProjectIdAndAnalyzerName(projectId, resource.getAnalyzerName());
        if (entity == null) {
            entity = new AnalyzerConfiguration();
            entity.setProject(project);
        }
        assembler.updateEntity(entity, resource);
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
        checkProjectExistsOrThrowException(projectId);
        List<AnalyzerConfiguration> configurations = analyzerConfigurationRepository.findByProjectId(projectId);
        AnalyzerConfigurationResourceAssembler assembler = new AnalyzerConfigurationResourceAssembler(projectId);
        return new ResponseEntity<>(assembler.toResourceList(configurations), HttpStatus.OK);
    }

    private Project loadProjectOrThrowException(Long projectId) {
        Project project = projectRepository.findOne(projectId);
        if (project == null) {
            throw new ValidationException("projectId", "Project does not exist");
        } else {
            return project;
        }
    }

    private void checkAnalyzerExistsOrThrowException(String analyzerName) {
        SourceCodeFileAnalyzerPlugin analyzer = analyzerRegistry.getAnalyzer(analyzerName);
        if (analyzer == null) {
            throw new ValidationException("analyzerName", "No analyzer plugin with this name exists!");
        }
    }

    /**
     * Checks if the Project with the given ID exists without loading it from the database.
     */
    private void checkProjectExistsOrThrowException(Long projectId) {
        int count = projectRepository.countById(projectId);
        if (count == 0) {
            throw new ValidationException("projectId", "Project does not exist");
        }
    }

}