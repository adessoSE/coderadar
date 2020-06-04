package io.reflectoring.coderadar.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Helper class to make it possible to resolve {@link
 * org.springframework.beans.factory.annotation.Autowired} annotations on beans that are not
 * themselves part of the Spring application context.
 */
@Component
public class Injector implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  private static final Injector INSTANCE = new Injector();

  private Injector() {}

  public static Injector getInstance() {
    return INSTANCE;
  }

  /**
   * Resolves {@link org.springframework.beans.factory.annotation.Autowired} annotations on the
   * fields of the given object and injects them with beans from the Spring application context.
   *
   * @param bean the bean whose fields to inject.
   */
  public void inject(Object bean) {
    applicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
  }

  @Override
  public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
    Injector.applicationContext = applicationContext;
  }
}
