package org.wickedsource.coderadar.module.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.commit.domain.CommitToFileAssociation;
import org.wickedsource.coderadar.commit.event.CommitToFileAssociatedEvent;
import org.wickedsource.coderadar.module.domain.Module;
import org.wickedsource.coderadar.module.domain.ModuleRepository;

/**
* Service that associates files with modules. The methods of this service will run asynchronously
* since it may take a while associating all files in the database with a certain module.
*/
@Service
public class ModuleAssociationService {

	private ModuleRepository moduleRepository;

	@Autowired
	public ModuleAssociationService(ModuleRepository moduleRepository) {
		this.moduleRepository = moduleRepository;
	}

	/** Associates the given module with all files whose path is within the path of the module. */
	public void associate(Module module) {
		List<CommitToFileAssociation> files = moduleRepository.findFilesByModulePath(module.getPath());
		for (CommitToFileAssociation file : files) {
			file.getModules().add(module);
		}
	}

	/**
	* Disassociates all files of the given module from that module before associating the given
	* module with all files whose path is within the path of the module.
	*/
	public void reassociate(Module module) {
		disassociate(module);
		associate(module);
	}

	/** Disassociates all files of the given module from that module. */
	public void disassociate(Module module) {
		List<CommitToFileAssociation> oldFiles = moduleRepository.findFilesByModuleId(module.getId());
		for (CommitToFileAssociation file : oldFiles) {
			file.getModules().remove(module);
		}
	}

	/** Associates the given file with all modules in whose path the file lies. */
	@EventListener
	public void associate(CommitToFileAssociatedEvent event) {
		CommitToFileAssociation file = event.getAssociation();
		List<Module> modules = moduleRepository.findModulesForFile(file.getSourceFile().getFilepath());
		for (Module module : modules) {
			file.getModules().add(module);
		}
	}
}
