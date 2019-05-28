package io.reflectoring.coderadar.rest.analyzing;

import io.reflectoring.coderadar.core.analyzer.port.driver.StopAnalyzingUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StopAnalyzingController {
    private final StopAnalyzingUseCase stopAnalyzingUseCase;

    @Autowired
    public StopAnalyzingController(StopAnalyzingUseCase stopAnalyzingUseCase) {
        this.stopAnalyzingUseCase = stopAnalyzingUseCase;
    }
}
