package io.reflectoring.coderadar.core.projectadministration.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Date;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@Data
@NodeEntity
public class AnalyzingJob {
    private Long id;
    private Date from; // TODO: Maybe use date converter.
    private boolean active;
    private boolean rescan;

    @Relationship(direction = INCOMING)
    private Project project;
}
