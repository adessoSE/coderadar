package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.port.driver.GetAuthorsUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class GetAuthorsController {
    private final GetAuthorsUseCase getAuthorsUseCase;

    public GetAuthorsController(GetAuthorsUseCase getAuthorsUseCase) {
        this.getAuthorsUseCase = getAuthorsUseCase;
    }

    @GetMapping(path = "/projects/{projectId}/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getNumberOfAuthors(@PathVariable("projectId") Long projectId) {
        return new ResponseEntity<>(getAuthorsUseCase.getNumberOfAuthors(projectId), HttpStatus.OK);
    }
}
