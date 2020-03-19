package io.reflectoring.coderadar.contributor;

import io.reflectoring.coderadar.EntityNotFoundException;

public class ContributorNotFoundException extends EntityNotFoundException {
  public ContributorNotFoundException(long id) {
    super("Contributor with id " + id + " not found.");
  }
}
