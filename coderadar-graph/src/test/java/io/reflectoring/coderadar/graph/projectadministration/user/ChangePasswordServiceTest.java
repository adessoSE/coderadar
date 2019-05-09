package io.reflectoring.coderadar.graph.projectadministration.user;

import io.reflectoring.coderadar.graph.projectadministration.user.repository.ChangePasswordRepository;
import io.reflectoring.coderadar.graph.projectadministration.user.service.ChangePasswordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Change password")
public class ChangePasswordServiceTest {
    @Mock
    private ChangePasswordRepository changePasswordRepository;

    @InjectMocks
    @Qualifier("ChangePasswordServiceNeo4j")
    private ChangePasswordService changePasswordService;
}
