package io.reflectoring.coderadar.useradministration;

import io.reflectoring.coderadar.EntityNotFoundException;

public class TeamNotFoundException extends EntityNotFoundException {
    public TeamNotFoundException(Long teamId) {
        super("Team with id " + teamId + " not found.");
    }
}
