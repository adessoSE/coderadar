package io.reflectoring.coderadar.projectadministration;

public class CommitNotFoundException extends EntityNotFoundException {
    public CommitNotFoundException(Long id) {
        super(String.format("Commit with id %d not found!", id));
    }
}
