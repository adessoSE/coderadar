package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.ListFilePatternsOfProjectPort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("ListFilePatternsOfProjectServiceNeo4j")
public class ListFilePatternsOfProjectService implements ListFilePatternsOfProjectPort {
    @Override
    public List<FilePattern> listFilePatterns(Long projectId) {
        return new LinkedList<>();
    }
}
