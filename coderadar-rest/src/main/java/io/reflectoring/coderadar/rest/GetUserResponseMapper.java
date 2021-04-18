package io.reflectoring.coderadar.rest;

import io.reflectoring.coderadar.domain.User;
import io.reflectoring.coderadar.rest.domain.GetUserResponse;
import java.util.ArrayList;
import java.util.List;

public class GetUserResponseMapper {
  private GetUserResponseMapper() {}

  public static GetUserResponse mapUser(User user) {
    return new GetUserResponse(user.getId(), user.getUsername(), user.isPlatformAdmin());
  }

  public static List<GetUserResponse> mapUsers(List<User> users) {
    List<GetUserResponse> result = new ArrayList<>(users.size());
    for (User user : users) {
      result.add(mapUser(user));
    }
    return result;
  }
}
