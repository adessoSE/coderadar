package org.wickedsource.coderadar.analyzerconfig.rest;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.wickedsource.coderadar.plugin.ConfigurableAnalyzerPlugin;
import org.wickedsource.coderadar.plugin.SourceCodeFileAnalyzerPlugin;
import org.wickedsource.coderadar.analyzer.service.AnalyzerPluginRegistry;
import org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationFile;
import org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationFileRepository;
import org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationRepository;
import org.wickedsource.coderadar.core.common.ResourceNotFoundException;
import org.wickedsource.coderadar.core.rest.validation.ValidationException;
import org.wickedsource.coderadar.projectadministration.domain.AnalyzerConfiguration;

@Controller
@Transactional
@RequestMapping(path = "/projects/{projectId}/analyzers/{analyzerConfigurationId}/file")
public class AnalyzerConfigurationFileController {

  private AnalyzerConfigurationFileRepository analyzerConfigurationFileRepository;

  private AnalyzerConfigurationRepository analyzerConfigurationRepository;

  private AnalyzerPluginRegistry analyzerRegistry;

  @Autowired
  public AnalyzerConfigurationFileController(
      AnalyzerConfigurationFileRepository analyzerConfigurationFileRepository,
      AnalyzerConfigurationRepository analyzerConfigurationRepository,
      AnalyzerPluginRegistry analyzerRegistry) {
    this.analyzerConfigurationFileRepository = analyzerConfigurationFileRepository;
    this.analyzerConfigurationRepository = analyzerConfigurationRepository;
    this.analyzerRegistry = analyzerRegistry;
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<String> uploadConfigurationFile(
      @PathVariable Long projectId,
      @PathVariable Long analyzerConfigurationId,
      @RequestParam("file") MultipartFile file) {
    try {
      AnalyzerConfiguration configuration =
          analyzerConfigurationRepository.findByProjectIdAndId(projectId, analyzerConfigurationId);
      if (configuration == null) {
        throw new ResourceNotFoundException();
      }
      checkAnalyzer(configuration.getAnalyzerName(), file.getBytes());

      AnalyzerConfigurationFile configFile =
          analyzerConfigurationFileRepository
              .findByAnalyzerConfigurationProjectIdAndAnalyzerConfigurationId(
                  projectId, analyzerConfigurationId);
      if (configFile == null) {
        configFile = new AnalyzerConfigurationFile();
      }

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
  public ResponseEntity<ByteArrayResource> downloadConfigurationFile(
      @PathVariable Long projectId, @PathVariable Long analyzerConfigurationId) {
    AnalyzerConfigurationFile configFile =
        analyzerConfigurationFileRepository
            .findByAnalyzerConfigurationProjectIdAndAnalyzerConfigurationId(
                projectId, analyzerConfigurationId);
    if (configFile == null) {
      throw new ResourceNotFoundException();
    }

    return ResponseEntity.ok()
        .contentLength(configFile.getSizeInBytes())
        .contentType(MediaType.parseMediaType(configFile.getContentType()))
        .body(new ByteArrayResource(configFile.getFileData()));
  }

  private void checkAnalyzer(String analyzerName, byte[] configurationFile) {
    SourceCodeFileAnalyzerPlugin analyzer = analyzerRegistry.createAnalyzer(analyzerName);
    if (analyzer == null) {
      throw new ValidationException(
          "file", String.format("No analyzer plugin with the name %s exists!", analyzerName));
    }
    if (!(analyzer instanceof ConfigurableAnalyzerPlugin)) {
      throw new ValidationException(
          "file",
          String.format(
              "Analyzer with name %s is not configurable with a configuration file!",
              analyzerName));
    }
    ConfigurableAnalyzerPlugin configurableAnalyzer = (ConfigurableAnalyzerPlugin) analyzer;
    if (!configurableAnalyzer.isValidConfigurationFile(configurationFile)) {
      throw new ValidationException(
          "file",
          String.format(
              "The provided configuration file is not a valid file for analyzer %s!",
              analyzerName));
    }
  }
}
