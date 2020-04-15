package io.reflectoring.coderadar.graph;

import java.util.ArrayList;
import java.util.List;

public interface Mapper<T, E> {

  T mapGraphObject(E nodeEntity);

  E mapDomainObject(T domainObject);

  default List<T> mapNodeEntities(List<E> graphObjects) {
    List<T> domainObjects = new ArrayList<>(graphObjects.size());
    for (E e : graphObjects) {
      domainObjects.add(mapGraphObject(e));
    }
    return domainObjects;
  }

  default List<E> mapDomainObjects(List<T> domainObjects) {
    List<E> graphObjects = new ArrayList<>(domainObjects.size());
    for (T t : domainObjects) {
      graphObjects.add(mapDomainObject(t));
    }
    return graphObjects;
  }
}
