package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.core.query.port.driver.GetAvailableMetricsInProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAvailableMetricsInProjectController {
    private final GetAvailableMetricsInProjectUseCase getAvailableMetricsInProjectUseCase;

    @Autowired
    public GetAvailableMetricsInProjectController(GetAvailableMetricsInProjectUseCase getAvailableMetricsInProjectUseCase) {
        this.getAvailableMetricsInProjectUseCase = getAvailableMetricsInProjectUseCase;
    }

    @GetMapping(path = "/projects/{projectId}/metrics")
    public ResponseEntity<List<String>> getMetrics(@PathVariable("projectId") Long projectId) {
        return new ResponseEntity<>(getAvailableMetricsInProjectUseCase.get(projectId), HttpStatus.OK);
    }
}
