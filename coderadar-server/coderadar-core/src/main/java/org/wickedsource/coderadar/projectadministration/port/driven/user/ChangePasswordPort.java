package org.wickedsource.coderadar.projectadministration.port.driven.user;

public interface ChangePasswordPort {
    void changePassword(Long id, String newPassword);
}
