package io.reflectoring.coderadar.graph.projectadministration.user.service;

import io.reflectoring.coderadar.core.projectadministration.port.driven.user.ChangePasswordPort;
import org.springframework.stereotype.Service;

@Service("ChangePasswordServiceNeo4j")
public class ChangePasswordService implements ChangePasswordPort {
    @Override
    public void changePassword(String refreshToken, String newPassword) {

    }
}
