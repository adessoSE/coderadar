package org.wickedsource.coderadar.core.configuration;

import org.junit.Test;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class InjectorTest extends IntegrationTestTemplate {

  @Test
  public void fieldInjection() {
    FieldInjectorTestSubject subject = new FieldInjectorTestSubject();
    Injector.getInstance().inject(subject);
    assertThat(subject.getInjectedConfiguration()).isNotNull();
  }
}
