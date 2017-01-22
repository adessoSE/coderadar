package org.wickedsource.coderadar.metricquery.rest.tree;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.analyzer.api.ChangeType;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.core.rest.validation.ResourceNotFoundException;
import org.wickedsource.coderadar.metric.domain.metricvalue.ChangedFileDTO;
import org.wickedsource.coderadar.metric.domain.metricvalue.GroupedMetricValueDTO;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValue;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository;
import org.wickedsource.coderadar.metricquery.rest.tree.delta.ChangedFilesMap;
import org.wickedsource.coderadar.metricquery.rest.tree.delta.Changes;
import org.wickedsource.coderadar.metricquery.rest.tree.delta.DeltaTreePayload;
import org.wickedsource.coderadar.metricquery.rest.tree.delta.DeltaTreeQuery;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;

@Controller
@ExposesResourceFor(MetricValue.class)
@Transactional
@RequestMapping(path = "/projects/{projectId}/metricvalues")
public class MetricsTreeController {

  private ProjectVerifier projectVerifier;

  private MetricValueRepository metricValueRepository;

  private CommitRepository commitRepository;

  @Autowired
  public MetricsTreeController(
      ProjectVerifier projectVerifier,
      MetricValueRepository metricValueRepository,
      CommitRepository commitRepository) {
    this.projectVerifier = projectVerifier;
    this.metricValueRepository = metricValueRepository;
    this.commitRepository = commitRepository;
  }

  @RequestMapping(
    path = "/tree",
    method = {RequestMethod.GET, RequestMethod.POST},
    produces = "application/hal+json"
  )
  public ResponseEntity<MetricsTreeResource> queryMetricsForSingleCommit(
      @PathVariable Long projectId, @Valid @RequestBody MetricsTreeQuery query) {
    projectVerifier.checkProjectExistsOrThrowException(projectId);
    Commit commit = commitRepository.findByName(query.getCommit());
    if (commit == null) {
      throw new ResourceNotFoundException();
    }
    List<GroupedMetricValueDTO> groupedMetricValues = new ArrayList<>();
    groupedMetricValues.addAll(
        metricValueRepository.findValuesAggregatedByModule(
            projectId, commit.getSequenceNumber(), query.getMetrics()));
    groupedMetricValues.addAll(
        metricValueRepository.findValuesAggregatedByFile(
            projectId, commit.getSequenceNumber(), query.getMetrics()));
    MetricsTreeResourceAssembler assembler = new MetricsTreeResourceAssembler();
    MetricsTreeResource metricsTreeResource = assembler.toResource(groupedMetricValues);
    return new ResponseEntity<>(metricsTreeResource, HttpStatus.OK);
  }

  @RequestMapping(
    path = "/deltaTree",
    method = {RequestMethod.GET, RequestMethod.POST},
    produces = "application/hal+json"
  )
  public ResponseEntity<MetricsTreeResource> queryMetricsDelta(
      @PathVariable Long projectId, @Valid @RequestBody DeltaTreeQuery query) {
    projectVerifier.checkProjectExistsOrThrowException(projectId);
    Commit commit1 = commitRepository.findByName(query.getCommit1());
    if (commit1 == null) {
      throw new ResourceNotFoundException("commit1 does not exist.");
    }

    Commit commit2 = commitRepository.findByName(query.getCommit2());
    if (commit2 == null) {
      throw new ResourceNotFoundException("commit2 does not exist.");
    }

    MetricsTreeResourceAssembler assembler = new MetricsTreeResourceAssembler();

    List<GroupedMetricValueDTO> groupedMetricValues1 = new ArrayList<>();
    groupedMetricValues1.addAll(
        metricValueRepository.findValuesAggregatedByModule(
            projectId, commit1.getSequenceNumber(), query.getMetrics()));
    groupedMetricValues1.addAll(
        metricValueRepository.findValuesAggregatedByFile(
            projectId, commit1.getSequenceNumber(), query.getMetrics()));
    MetricsTreeResource<CommitMetricsPayload> treeForCommit1 =
        assembler.toResource(groupedMetricValues1);

    List<GroupedMetricValueDTO> groupedMetricValues2 = new ArrayList<>();
    groupedMetricValues2.addAll(
        metricValueRepository.findValuesAggregatedByModule(
            projectId, commit2.getSequenceNumber(), query.getMetrics()));
    groupedMetricValues2.addAll(
        metricValueRepository.findValuesAggregatedByFile(
            projectId, commit2.getSequenceNumber(), query.getMetrics()));
    MetricsTreeResource<CommitMetricsPayload> treeForCommit2 =
        assembler.toResource(groupedMetricValues2);

    ChangedFilesMap changedFilesMap = getChangedFiles(projectId, commit1, commit2);

    MetricsTreeResource<DeltaTreePayload> deltaTree =
        merge(treeForCommit1, treeForCommit2, changedFilesMap);

    return new ResponseEntity<>(deltaTree, HttpStatus.OK);
  }

  private ChangedFilesMap getChangedFiles(Long projectId, Commit commit1, Commit commit2) {
    List<ChangedFileDTO> changedFiles =
        metricValueRepository.findChangedFilesBetweenTwoCommits(
            projectId, commit1.getSequenceNumber(), commit2.getSequenceNumber());
    ChangedFilesMap changedFilesMap = new ChangedFilesMap();
    for (ChangedFileDTO changedFile : changedFiles) {
      if (changedFile.getChangeType() == ChangeType.RENAME) {
        changedFilesMap.addRenamedFile(changedFile.getOldFileName(), changedFile.getNewFileName());
      }
      if (changedFile.getChangeType() == ChangeType.DELETE) {
        // for deleted files we have to take the old filename, since the new filename is always "/dev/null"
        changedFilesMap.addChangeType(changedFile.getOldFileName(), changedFile.getChangeType());
      } else {
        changedFilesMap.addChangeType(changedFile.getNewFileName(), changedFile.getChangeType());
      }
    }
    return changedFilesMap;
  }

  private MetricsTreeResource<DeltaTreePayload> merge(
      MetricsTreeResource<CommitMetricsPayload> tree1,
      MetricsTreeResource<CommitMetricsPayload> tree2,
      ChangedFilesMap changedFilesMap) {

    DeltaTreePayload rootPayload = new DeltaTreePayload();
    rootPayload.setCommit1Metrics(tree1.getPayload().getMetrics());
    rootPayload.setCommit2Metrics(tree2.getPayload().getMetrics());

    MetricsTreeResource<DeltaTreePayload> deltaTree = new MetricsTreeResource<>(rootPayload);

    addMetricsToDeltaTreeRecursively(tree1, deltaTree, (DeltaTreePayload::setCommit1Metrics));
    addMetricsToDeltaTreeRecursively(tree2, deltaTree, (DeltaTreePayload::setCommit2Metrics));
    addRenamedInfoRecursively(deltaTree, changedFilesMap);
    return deltaTree;
  }

  private void addMetricsToDeltaTreeRecursively(
      MetricsTreeResource<CommitMetricsPayload> sourceTree,
      MetricsTreeResource<DeltaTreePayload> deltaTree,
      PayloadModifier addFunction) {
    for (MetricsTreeResource<CommitMetricsPayload> sourceNode : sourceTree.getChildren()) {
      MetricsTreeResource<DeltaTreePayload> targetNode = deltaTree.getChild(sourceNode.getName());
      if (targetNode == null) {
        DeltaTreePayload payload = new DeltaTreePayload();
        addFunction.modifyPayload(payload, sourceNode.getPayload().getMetrics());
        targetNode =
            new MetricsTreeResource<>(
                sourceNode.getName(), sourceTree, payload, sourceNode.getType());
        deltaTree.addChild(targetNode);
      } else {
        addFunction.modifyPayload(targetNode.getPayload(), sourceNode.getPayload().getMetrics());
      }
      addMetricsToDeltaTreeRecursively(sourceNode, targetNode, addFunction);
    }
  }

  private void addRenamedInfoRecursively(
      MetricsTreeResource<DeltaTreePayload> tree, ChangedFilesMap changedFilesMap) {
    for (MetricsTreeResource<DeltaTreePayload> node : tree.getChildren()) {
      String fileName = node.getName();
      String newFileName = changedFilesMap.getNewFileName(fileName);
      String oldFileName = changedFilesMap.getOldFileName(fileName);

      if (newFileName != null) {
        node.getPayload().setRenamedTo(newFileName);
      }

      if (oldFileName != null) {
        node.getPayload().setRenamedFrom(oldFileName);
      }

      if (node.getType() == MetricsTreeNodeType.FILE) {
        List<ChangeType> changeTypes = changedFilesMap.getChangeTypes(fileName);
        if (changeTypes == null) {
          changeTypes = new ArrayList<>();
        }
        Changes changes = new Changes();
        changes.setAdded(changeTypes.contains(ChangeType.ADD));
        changes.setModified(changeTypes.contains(ChangeType.MODIFY));
        changes.setDeleted(changeTypes.contains(ChangeType.DELETE));
        changes.setRenamed(
            changeTypes.contains(ChangeType.RENAME) || newFileName != null || oldFileName != null);
        node.getPayload().setChanges(changes);
      }

      addRenamedInfoRecursively(node, changedFilesMap);
    }
  }

  @FunctionalInterface
  interface PayloadModifier {
    void modifyPayload(DeltaTreePayload payload, MetricValuesSet metrics);
  }
}
