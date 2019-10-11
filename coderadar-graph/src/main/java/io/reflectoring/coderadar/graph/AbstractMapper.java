package io.reflectoring.coderadar.graph;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractMapper<T, E> {

  public abstract T mapNodeEntity(E nodeEntity);

  public abstract E mapDomainObject(T domainObject);

  public Collection<T> mapNodeEntities(Iterable<E> nodeEntities) {
    Collection<T> domainObjects = new ArrayList<>();
    for (E e : nodeEntities) {
      domainObjects.add(mapNodeEntity(e));
    }
    return domainObjects;
  }

  public Collection<E> mapDomainObjects(Iterable<T> domainObjects) {
    Collection<E> nodeEntities = new ArrayList<>();
    for (T t : domainObjects) {
      nodeEntities.add(mapDomainObject(t));
    }
    return nodeEntities;
  }
}
