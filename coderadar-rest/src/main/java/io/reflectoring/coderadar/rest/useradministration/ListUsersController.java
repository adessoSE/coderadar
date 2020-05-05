package io.reflectoring.coderadar.rest.useradministration;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.port.driver.get.ListUsersUseCase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class ListUsersController implements AbstractBaseController {
  private final ListUsersUseCase listUsersUseCase;

  public ListUsersController(ListUsersUseCase listUsersUseCase) {
    this.listUsersUseCase = listUsersUseCase;
  }
}
