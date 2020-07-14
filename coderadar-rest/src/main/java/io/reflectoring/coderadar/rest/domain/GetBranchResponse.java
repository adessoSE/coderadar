package io.reflectoring.coderadar.rest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class GetBranchResponse {
  private String name;
  private String commitHash;
  private boolean isTag;

  public String getName() {
    return name;
  }

  public String getCommitHash() {
    return commitHash;
  }

  @JsonProperty(value="isTag")
  public boolean isTag() {
    return isTag;
  }
}
