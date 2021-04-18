package io.reflectoring.coderadar.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objects of this class provide parameters to query for quality profile ratings aggregated per
 * commit.
 */
@NoArgsConstructor
@Data
public class CommitProfileRatingsQuery {

  @NotNull private String commit;

  @Size(min = 1)
  private List<String> profiles;

  private void initProfiles() {
    if (profiles == null) {
      profiles = new ArrayList<>();
    }
  }

  public void addProfile(String profileName) {
    initProfiles();
    this.profiles.add(profileName);
  }

  public void addProfiles(String... profileNames) {
    initProfiles();
    this.profiles.addAll(Arrays.asList(profileNames));
  }
}
