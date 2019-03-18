package org.wickedsource.coderadar.core.rest;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractResourceAssembler<E, R> {

  public List<R> toResourceList(Iterable<E> entities) {
    List<R> resourceList = new ArrayList<>();
    for (E entity : entities) {
      resourceList.add(toResource(entity));
    }
    return resourceList;
  }

  protected abstract R toResource(E entity);
}
