package org.wickedsource.coderadar.core.configuration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
* Helper class to make it possible to resolve {@link
* org.springframework.beans.factory.annotation.Autowired} annotations on beans that are not
* themselves part of the Spring application context.
*/
@Component
public class Injector implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	private static Injector INSTANCE = new Injector();

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
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Injector.applicationContext = applicationContext;
	}
}
