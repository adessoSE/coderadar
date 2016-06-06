package org.wickedsource.coderadar.analyzer.rest;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfiguration;

import java.util.ArrayList;
import java.util.List;

public class AnalyzerConfigurationResourceAssembler extends ResourceAssemblerSupport<AnalyzerConfiguration, AnalyzerConfigurationResource> {

    AnalyzerConfigurationResourceAssembler() {
        super(AnalyzerConfigurationController.class, AnalyzerConfigurationResource.class);
    }

    @Override
    public AnalyzerConfigurationResource toResource(AnalyzerConfiguration entity) {
        return new AnalyzerConfigurationResource(entity.getAnalyzerName(), entity.getEnabled());
    }

    public List<AnalyzerConfigurationResource> toResourceList(List<AnalyzerConfiguration> entities) {
        List<AnalyzerConfigurationResource> resultList = new ArrayList<>();
        for (AnalyzerConfiguration entity : entities) {
            resultList.add(toResource(entity));
        }
        return resultList;
    }

    public AnalyzerConfiguration updateEntity(AnalyzerConfiguration entity, AnalyzerConfigurationResource resource) {
        entity.setAnalyzerName(resource.getAnalyzerName());
        entity.setEnabled(resource.isEnabled());
        return entity;
    }
}
