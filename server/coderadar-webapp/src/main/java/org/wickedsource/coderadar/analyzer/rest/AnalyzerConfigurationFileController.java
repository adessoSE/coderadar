package org.wickedsource.coderadar.analyzer.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfiguration;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfigurationFile;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfigurationFileRepository;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfigurationRepository;
import org.wickedsource.coderadar.core.rest.validation.ResourceNotFoundException;

import java.io.IOException;

@Controller
@ExposesResourceFor(AnalyzerConfiguration.class)
@Transactional
@RequestMapping(path = "/projects/{projectId}/analyzers/{analyzerConfigurationId}/file")
public class AnalyzerConfigurationFileController {

    @Autowired
    private AnalyzerConfigurationFileRepository analyzerConfigurationFileRepository;

    @Autowired
    private AnalyzerConfigurationRepository analyzerConfigurationRepository;

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<String> uploadConfigurationFile(@PathVariable Long projectId, @PathVariable Long analyzerConfigurationId, MultipartFile file) {
        AnalyzerConfiguration configuration = analyzerConfigurationRepository.findByProjectIdAndId(projectId, analyzerConfigurationId);
        if (configuration == null) {
            throw new ResourceNotFoundException();
        }

        AnalyzerConfigurationFile configFile = analyzerConfigurationFileRepository.findByAnalyzerConfigurationProjectIdAndAnalyzerConfigurationId(projectId, analyzerConfigurationId);
        if (configFile == null) {
            configFile = new AnalyzerConfigurationFile();
        }

        try {
            configFile.setAnalyzerConfiguration(configuration);
            configFile.setContentType(file.getContentType());
            configFile.setFileName(file.getOriginalFilename());
            configFile.setSizeInBytes(file.getSize());
            configFile.setFileData(file.getBytes());

            analyzerConfigurationFileRepository.save(configFile);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> downloadConfigurationFile(@PathVariable Long projectId, @PathVariable Long analyzerConfigurationId) {
        AnalyzerConfigurationFile configFile = analyzerConfigurationFileRepository.findByAnalyzerConfigurationProjectIdAndAnalyzerConfigurationId(projectId, analyzerConfigurationId);
        if (configFile == null) {
            throw new ResourceNotFoundException();
        }

        return ResponseEntity.ok()
                .contentLength(configFile.getSizeInBytes())
                .contentType(MediaType.parseMediaType(configFile.getContentType()))
                .body(new ByteArrayResource(configFile.getFileData()));

    }


}
