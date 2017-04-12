package org.wickedsource.coderadar.graph.domain.filesnapshot;

import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface FileSnapshotNodeRepository extends GraphRepository<FileSnapshotNode> {

  @Query(
      "MATCH "
          + "(snapshot:FileSnapshot)-[:SNAPSHOT_IN_COMMIT]->(c:Commit {name:{0}})"
          + "RETURN snapshot")
  List<FileSnapshotNode> findFileSnapshotsInCommit(String commitName);

  @Query("MATCH " + "")
  List<FileSnapshotNode> findSumInCommit(String commitName, String metricName);
}
