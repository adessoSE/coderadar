package io.reflectoring.coderadar.rest.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.GetFilePatternFromProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/projects/{projectId}/files")
public class GetFilePatternsFromProjectController {

    private final GetFilePatternFromProjectUseCase getFilePatternFromProjectUseCase;

    @Autowired
    public GetFilePatternsFromProjectController(GetFilePatternFromProjectUseCase getFilePatternFromProjectUseCase) {
        this.getFilePatternFromProjectUseCase = getFilePatternFromProjectUseCase;
    }

    @GetMapping
    public ResponseEntity<List<FilePattern>> getFilePatterns(@PathVariable Long projectId) {
        return new ResponseEntity<>(getFilePatternFromProjectUseCase.getFilePatterns(projectId), HttpStatus.OK);
    }
}
