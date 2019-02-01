package org.wickedsource.coderadar.factories.resources;

import org.wickedsource.coderadar.module.rest.ModuleResource;

public class ModuleResourceFactory {

	public static ModuleResource module() {
		ModuleResource resource = new ModuleResource();
		resource.setModulePath("server/coderadar-webapp");
		return resource;
	}
}
