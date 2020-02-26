package io.reflectoring.coderadar.contributor.port.driven;

public interface MergeContributorsPort {
  void mergeContributors(Long firstId, Long secondId, String displayName);
}
