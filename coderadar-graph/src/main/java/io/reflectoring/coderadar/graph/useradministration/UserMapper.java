package io.reflectoring.coderadar.graph.useradministration;

import io.reflectoring.coderadar.domain.User;
import io.reflectoring.coderadar.graph.Mapper;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;

public class UserMapper implements Mapper<User, UserEntity> {

  @Override
  public User mapGraphObject(UserEntity nodeEntity) {
    User user = new User();
    user.setId(nodeEntity.getId());
    user.setUsername(nodeEntity.getUsername());
    user.setPassword(nodeEntity.getPassword());
    user.setPlatformAdmin(nodeEntity.isPlatformAdmin());
    return user;
  }

  @Override
  public UserEntity mapDomainObject(User domainObject) {
    UserEntity userEntity = new UserEntity();
    userEntity.setPassword(domainObject.getPassword());
    userEntity.setUsername(domainObject.getUsername());
    userEntity.setPlatformAdmin(domainObject.isPlatformAdmin());
    return userEntity;
  }
}
