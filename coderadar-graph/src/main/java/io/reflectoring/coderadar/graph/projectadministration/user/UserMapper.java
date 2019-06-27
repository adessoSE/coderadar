package io.reflectoring.coderadar.graph.projectadministration.user;

import io.reflectoring.coderadar.graph.AbstractMapper;
import io.reflectoring.coderadar.graph.projectadministration.domain.UserEntity;
import io.reflectoring.coderadar.projectadministration.domain.User;

public class UserMapper extends AbstractMapper<User, UserEntity> {

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
    userEntity.setId(domainObject.getId());
    userEntity.setPassword(domainObject.getPassword());
    userEntity.setUsername(domainObject.getUsername());
    return userEntity;
  }
}
