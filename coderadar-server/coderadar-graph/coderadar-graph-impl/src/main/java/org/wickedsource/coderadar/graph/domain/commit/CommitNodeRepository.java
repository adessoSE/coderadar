package org.wickedsource.coderadar.graph.domain.commit;

import java.util.Collection;
import java.util.Set;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface CommitNodeRepository extends GraphRepository<CommitNode> {

  CommitNode findByName(CommitName name);

  @Query("MATCH (c:Commit) WHERE c.name IN {0} RETURN c")
  Set<CommitNode> findByNameIn(Collection<String> name);
}
