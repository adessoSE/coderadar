package org.wickedsource.coderadar.graph.domain.filesnapshot;

import java.util.List;
import java.util.Set;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface FileSnapshotNodeRepository extends Neo4jRepository<FileSnapshotNode, Long> {

  @Query(
      "MATCH "
          + "(snapshot:FileSnapshot)-[:SNAPSHOT_IN_COMMIT]->(c:Commit {name:{0}})"
          + "RETURN snapshot")
  List<FileSnapshotNode> inCommit(String commitName);

  @Query(
      "MATCH "
          + "(child:Commit {name:{0}})-[:IS_CHILD_OF*]->(parent:Commit), "
          + "(parent)-[:TOUCHED]->(file:File), "
          + "(snapshot:FileSnapshot)-[snapshotOfFile:SNAPSHOT_OF_FILE]->(file)"
          + "WHERE NOT ((file)-[:DELETED_IN_COMMIT]->()) "
          + "RETURN snapshot, snapshotOfFile, file")
  Set<FileSnapshotNode> notDeletedInPreviousCommits(String commitName);
}
