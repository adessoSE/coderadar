package io.reflectoring.coderadar.rest.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTeamResponse {
  private long id;
  private String name;
  private List<GetUserResponse> members;
}
