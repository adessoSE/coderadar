package org.wickedsource.coderadar.commit.configuration;

import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.configuration.MappingResourceProvider;

import java.util.Collections;
import java.util.Set;

@Component
public class CommitMappingResourceProvider implements MappingResourceProvider {

    @Override
    public Set<String> getMappingResource() {
        return Collections.singleton("org/wickedsource/coderadar/commit/domain/Commit.orm.xml");
    }

}
