package io.reflectoring.coderadar.graph;

import java.util.ArrayList;
import java.util.List;

public interface Mapper<T, E> {

  T mapNodeEntity(E nodeEntity);

  E mapDomainObject(T domainObject);

  default List<T> mapNodeEntities(List<E> nodeEntities) {
    List<T> domainObjects = new ArrayList<>(nodeEntities.size());
    for (E e : nodeEntities) {
      domainObjects.add(mapNodeEntity(e));
    }
    return domainObjects;
  }

  default List<E> mapDomainObjects(List<T> domainObjects) {
    List<E> nodeEntities = new ArrayList<>(domainObjects.size());
    for (T t : domainObjects) {
      nodeEntities.add(mapDomainObject(t));
    }
    return nodeEntities;
  }
}
