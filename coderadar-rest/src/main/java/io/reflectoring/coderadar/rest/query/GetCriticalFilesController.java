package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.port.driver.GetCriticalFilesUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class GetCriticalFilesController {
    private final GetCriticalFilesUseCase getCriticalFilesUseCase;

    public GetCriticalFilesController(GetCriticalFilesUseCase getCriticalFilesUseCase) {
        this.getCriticalFilesUseCase = getCriticalFilesUseCase;
    }

    @GetMapping(path = "/projects/{projectId}/files/critical")
    public ResponseEntity<List<String>> getCriticalFiles(@PathVariable Long projectId) {
        return new ResponseEntity<>(getCriticalFilesUseCase.getCriticalFiles(projectId), HttpStatus.OK);
    }
}
