package io.reflectoring.coderadar.graph;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMapper<T, E> {

  public abstract T mapNodeEntity(E nodeEntity);

  public abstract E mapDomainObject(T domainObject);

  public List<T> mapNodeEntities(Iterable<E> nodeEntities) {
    List<T> domainObjects = new ArrayList<>();
    for (E e : nodeEntities) {
      domainObjects.add(mapNodeEntity(e));
    }
    return domainObjects;
  }

  public List<E> mapDomainObjects(Iterable<T> domainObjects) {
    List<E> nodeEntities = new ArrayList<>();
    for (T t : domainObjects) {
      nodeEntities.add(mapDomainObject(t));
    }
    return nodeEntities;
  }
}
