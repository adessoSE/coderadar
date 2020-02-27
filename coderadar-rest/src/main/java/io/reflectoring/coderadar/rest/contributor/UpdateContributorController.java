package io.reflectoring.coderadar.rest.contributor;

import io.reflectoring.coderadar.contributor.port.driver.UpdateContributorCommand;
import io.reflectoring.coderadar.contributor.port.driver.UpdateContributorUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class UpdateContributorController {
    private final UpdateContributorUseCase updateContributorUseCase;

    public UpdateContributorController(UpdateContributorUseCase updateContributorUseCase) {
        this.updateContributorUseCase = updateContributorUseCase;
    }

    @PutMapping(path = "/contributors/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> updateContributor(@PathVariable Long id, @RequestBody @Validated UpdateContributorCommand command) {
        updateContributorUseCase.updateContributor(id, command);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
