package org.wickedsource.coderadar.core.configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

/**
* Picks up all implementations of {@link MappingResourceProvider} in the application context and
* retrieves their locations of ORM mapping files. It then registers these files with the
* EntityManagerFactory. This allows modules to define their own ORM mappings instead of having all
* in one place.
*/
@Component
public class RegisterMappingResources implements BeanPostProcessor {

	private List<MappingResourceProvider> mappingResourceProviders;

	@Autowired
	public RegisterMappingResources(List<MappingResourceProvider> mappingResourceProviders) {
		this.mappingResourceProviders = mappingResourceProviders;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		if (bean instanceof LocalContainerEntityManagerFactoryBean) {
			LocalContainerEntityManagerFactoryBean factory =
					(LocalContainerEntityManagerFactoryBean) bean;
			factory.setMappingResources(collectMappingResources().toArray(new String[] {}));
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	private Set<String> collectMappingResources() {
		Set<String> mappingResource = new HashSet<>();
		for (MappingResourceProvider provider : mappingResourceProviders) {
			mappingResource.addAll(provider.getMappingResource());
		}
		return mappingResource;
	}
}
