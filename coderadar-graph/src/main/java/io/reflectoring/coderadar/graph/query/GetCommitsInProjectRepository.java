package io.reflectoring.coderadar.graph.query;

import io.reflectoring.coderadar.core.analyzer.domain.Commit;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetCommitsInProjectRepository extends Neo4jRepository<Commit, Long> {}
