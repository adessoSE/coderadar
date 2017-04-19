package org.wickedsource.coderadar.graph.service;

import java.util.Objects;
import org.assertj.core.api.AbstractAssert;
import org.wickedsource.coderadar.graph.domain.commit.CommitNode;

public class CommitNodeAssert extends AbstractAssert<CommitNodeAssert, CommitNode> {

  public CommitNodeAssert(CommitNode actual) {
    super(actual, CommitNodeAssert.class);
  }

  public static CommitNodeAssert assertThat(CommitNode commitNode) {
    return new CommitNodeAssert(commitNode);
  }

  public CommitNodeAssert hasNoParent() {
    return hasParents(0);
  }

  public CommitNodeAssert hasParents(int parentCount) {
    isNotNull();
    if (Objects.isNull(actual.getParents())) {
      failWithMessage(
          "Expected CommitNode's parent count to be <%s> but CommitNode's parents was NULL",
          parentCount);
    }
    if (!Objects.equals(parentCount, actual.getParents().size())) {
      failWithMessage(
          "Expected CommitNode's parent count to be <%s> but was <%s>",
          parentCount, actual.getParents().size());
    }
    return this;
  }

  public CommitNodeAssert hasParent(CommitNode expectedParent) {
    hasParents(1);
    CommitNode actualParent = actual.getParents().iterator().next();
    if (!Objects.equals(expectedParent, actualParent)) {
      failWithMessage(
          "Expected CommitNode's parent count to be <%s> but was <%s>",
          expectedParent, actualParent);
    }
    return this;
  }

  public CommitNodeAssert hasTouchedFiles(int expectedCount) {
    isNotNull();
    if (Objects.isNull(actual.getTouchedFiles())) {
      failWithMessage(
          "Expected CommitNode's touchedFiles size to be <%s> but CommitNode's touchedFiles was NULL",
          expectedCount);
    }
    int actualCount = actual.getTouchedFiles().size();
    if (!Objects.equals(actualCount, expectedCount)) {
      failWithMessage(
          "Expected CommitNode's touchedFiles size to be <%s> but CommitNode's touchedFiles size was <%s>",
          actualCount);
    }
    return this;
  }

  public CommitNodeAssert hasTouchedFileSnapshots(int expectedCount) {
    isNotNull();
    if (Objects.isNull(actual.getTouchedFileSnapshots())) {
      failWithMessage(
          "Expected CommitNode's touchedFileSnapshots size to be <%s> but CommitNode's touchedFileSnapshots was NULL",
          expectedCount);
    }
    int actualCount = actual.getTouchedFileSnapshots().size();
    if (!Objects.equals(actualCount, expectedCount)) {
      failWithMessage(
          "Expected CommitNode's touchedFileSnapshots size to be <%s> but CommitNode's touchedFileSnapshots size was <%s>",
          expectedCount, actualCount);
    }
    return this;
  }
}
