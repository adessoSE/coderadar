package org.wickedsource.coderadar.core.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

public class InjectorTest extends IntegrationTestTemplate {

	@Test
	public void fieldInjection() {
		FieldInjectorTestSubject subject = new FieldInjectorTestSubject();
		Injector.getInstance().inject(subject);
		assertThat(subject.getInjectedConfiguration()).isNotNull();
	}
}
