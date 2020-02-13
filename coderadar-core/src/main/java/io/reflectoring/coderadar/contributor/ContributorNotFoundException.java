package io.reflectoring.coderadar.contributor;

import io.reflectoring.coderadar.EntityNotFoundException;

public class ContributorNotFoundException extends EntityNotFoundException {
  public ContributorNotFoundException(String s) {
    super(s);
  }

  public ContributorNotFoundException(Long id) {
    super("Contributor with id " + id + " not found.");
  }
}
