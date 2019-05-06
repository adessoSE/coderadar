package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.GetModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetModuleService implements GetModulePort {
    private final GetModuleRepository getModuleRepository;

    @Autowired
    public GetModuleService(GetModuleRepository getModuleRepository) {
        this.getModuleRepository = getModuleRepository;
    }

    @Override
    public Optional<Module> get(Long id) {
        return getModuleRepository.findById(id);
    }
}
