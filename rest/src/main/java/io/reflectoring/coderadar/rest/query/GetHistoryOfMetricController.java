package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.core.query.domain.Series;
import io.reflectoring.coderadar.core.query.port.driver.GetHistoryOfMetricCommand;
import io.reflectoring.coderadar.core.query.port.driver.GetHistoryOfMetricUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetHistoryOfMetricController {
    private final GetHistoryOfMetricUseCase getHistoryOfMetricUseCase;

    @Autowired
    public GetHistoryOfMetricController(GetHistoryOfMetricUseCase getHistoryOfMetricUseCase) {
        this.getHistoryOfMetricUseCase = getHistoryOfMetricUseCase;
    }

    @GetMapping(path = "/projects/{projectId}/metricvalues/history")
    public ResponseEntity<Series> getHistoryOfMetric(@Validated GetHistoryOfMetricCommand command) {
        return new ResponseEntity<>(getHistoryOfMetricUseCase.get(command), HttpStatus.OK);
    }
}
