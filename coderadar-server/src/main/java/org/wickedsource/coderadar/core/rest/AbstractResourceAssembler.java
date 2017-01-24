package org.wickedsource.coderadar.core.rest;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractResourceAssembler<E, R extends ResourceSupport>
    extends ResourceAssemblerSupport<E, R> {

  public AbstractResourceAssembler(Class<?> controllerClass, Class<R> resourceType) {
    super(controllerClass, resourceType);
  }

  public List<R> toResourceList(Iterable<E> entities) {
    List<R> resourceList = new ArrayList<>();
    for (E entity : entities) {
      resourceList.add(toResource(entity));
    }
    return resourceList;
  }
}
