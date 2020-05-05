package io.reflectoring.coderadar.graph.useradministration.domain;

import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
@Data
@NoArgsConstructor
public class TeamEntity {
    private Long id;
    private String name;

    @Relationship(value = "IS_IN", direction = Relationship.INCOMING)
    private List<UserEntity> members;

    private ProjectRole role;

}
