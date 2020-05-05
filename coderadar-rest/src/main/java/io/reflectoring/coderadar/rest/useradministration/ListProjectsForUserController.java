package io.reflectoring.coderadar.rest.useradministration;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.port.driver.get.ListProjectsForUserUseCase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class ListProjectsForUserController implements AbstractBaseController {
    private final ListProjectsForUserUseCase listProjectsForUserUseCase;

    public ListProjectsForUserController(ListProjectsForUserUseCase listProjectsForUserUseCase) {
        this.listProjectsForUserUseCase = listProjectsForUserUseCase;
    }
}
