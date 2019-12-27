package io.reflectoring.coderadar.graph;

import java.util.ArrayList;
import java.util.List;

public interface Mapper<T, E> {

  T mapNodeEntity(E nodeEntity);

  E mapDomainObject(T domainObject);

  default List<T> mapNodeEntities(Iterable<E> nodeEntities) {
    List<T> domainObjects = new ArrayList<>();
    for (E e : nodeEntities) {
      domainObjects.add(mapNodeEntity(e));
    }
    return domainObjects;
  }

  default List<E> mapDomainObjects(Iterable<T> domainObjects) {
    List<E> nodeEntities = new ArrayList<>();
    for (T t : domainObjects) {
      nodeEntities.add(mapDomainObject(t));
    }
    return nodeEntities;
  }
}
