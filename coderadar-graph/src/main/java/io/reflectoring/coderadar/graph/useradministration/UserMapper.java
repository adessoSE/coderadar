package io.reflectoring.coderadar.graph.useradministration;

import io.reflectoring.coderadar.graph.Mapper;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.useradministration.domain.User;

public class UserMapper implements Mapper<User, UserEntity> {

  @Override
  public User mapNodeEntity(UserEntity nodeEntity) {
    User user = new User();
    user.setId(nodeEntity.getId());
    user.setUsername(nodeEntity.getUsername());
    user.setPassword(nodeEntity.getPassword());
    return user;
  }

  @Override
  public UserEntity mapDomainObject(User domainObject) {
    UserEntity userEntity = new UserEntity();
    userEntity.setPassword(domainObject.getPassword());
    userEntity.setUsername(domainObject.getUsername());
    return userEntity;
  }
}
