package io.reflectoring.coderadar.rest.dependencyMap;

import io.reflectoring.coderadar.dependencyMap.port.driver.GetDependencyTreeUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DependencyTreeController {

    private final GetDependencyTreeUseCase getDependencyTreeUseCase;

    @Autowired
    public DependencyTreeController(GetDependencyTreeUseCase getDependencyTreeUseCase) {
        this.getDependencyTreeUseCase = getDependencyTreeUseCase;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{projectId}/structureMap/{commitName}")
    public ResponseEntity<Object> getDependencyTree(@PathVariable("projectId") Long projectId, @PathVariable("commitName") String commitName) {
        return ResponseEntity.ok(getDependencyTreeUseCase.getDependencyTree(projectId, commitName));
    }
}
