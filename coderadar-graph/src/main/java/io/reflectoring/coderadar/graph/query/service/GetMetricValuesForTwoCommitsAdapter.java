package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.query.repository.GetCommitsInProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetMetricValuesOfCommitRepository;
import io.reflectoring.coderadar.query.domain.Changes;
import io.reflectoring.coderadar.query.domain.MetricsTreeNodeType;
import io.reflectoring.coderadar.query.port.driven.GetMetricValuesOfTwoCommitsPort;
import io.reflectoring.coderadar.query.port.driven.MetricTree;
import io.reflectoring.coderadar.query.port.driver.DeltaTree;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForTwoCommitsCommand;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetMetricValuesForTwoCommitsAdapter implements GetMetricValuesOfTwoCommitsPort {

    private final GetMetricValuesOfCommitRepository getMetricValuesOfCommitRepository;
    private final GetCommitsInProjectRepository getCommitsInProjectRepository;
    private final FileRepository fileRepository;
    private final GetMetricsForAllFilesInCommitAdapter getMetricsForAllFilesInCommitAdapter;

    public GetMetricValuesForTwoCommitsAdapter(GetMetricValuesOfCommitRepository getMetricValuesOfCommitRepository, GetCommitsInProjectRepository getCommitsInProjectRepository, FileRepository fileRepository, GetMetricsForAllFilesInCommitAdapter getMetricsForAllFilesInCommitAdapter) {
        this.getMetricValuesOfCommitRepository = getMetricValuesOfCommitRepository;
        this.getCommitsInProjectRepository = getCommitsInProjectRepository;
        this.fileRepository = fileRepository;
        this.getMetricsForAllFilesInCommitAdapter = getMetricsForAllFilesInCommitAdapter;
    }

    @Override
    public DeltaTree get(GetMetricsForTwoCommitsCommand command, Long projectId) {
        MetricTree commit1Tree = getMetricsForAllFilesInCommitAdapter.get(new GetMetricsForCommitCommand(command.getCommit1(), command.getMetrics()), projectId);
        MetricTree commit2Tree = getMetricsForAllFilesInCommitAdapter.get(new GetMetricsForCommitCommand(command.getCommit2(), command.getMetrics()), projectId);
        String commit1Time = getCommitsInProjectRepository.findByNameAndProjectId(command.getCommit1(), projectId).getTimestamp().toInstant().toString();
        String commit2Time = getCommitsInProjectRepository.findByNameAndProjectId(command.getCommit2(), projectId).getTimestamp().toInstant().toString();
        return createDeltaTree(commit1Tree, commit2Tree, commit1Time, commit2Time, projectId);
    }

    private DeltaTree createDeltaTree(MetricTree commit1Tree, MetricTree commit2Tree, String commit1Time, String commit2Time, Long projectId) {
        DeltaTree deltaTree = new DeltaTree();
        deltaTree.setName(commit2Tree.getName());
        deltaTree.setType(commit2Tree.getType());
        deltaTree.setCommit1Metrics(commit1Tree.getMetrics());
        deltaTree.setCommit2Metrics(commit2Tree.getMetrics());

        List<String> addedFiles = new ArrayList<>();
        List<String> removedFiles = new ArrayList<>();

        for(int i = 0, j= 0; i < commit1Tree.getChildren().size() || j < commit2Tree.getChildren().size();){
            MetricTree metricTree2 = null;
            MetricTree metricTree1 = null;
            if(i < commit1Tree.getChildren().size()){
                metricTree1 = commit1Tree.getChildren().get(i);
            }
            if(j < commit2Tree.getChildren().size()){
                metricTree2 = commit2Tree.getChildren().get(j);
            }
            if(metricTree1 != null && metricTree2 != null && metricTree1.getName().equals(metricTree2.getName())){
                DeltaTree child = new DeltaTree();
                child.setName(metricTree1.getName());
                child.setType(MetricsTreeNodeType.FILE);
                child.setCommit1Metrics(metricTree1.getMetrics());
                child.setCommit2Metrics(metricTree2.getMetrics());
                Changes changes = new Changes();
                changes.setModified(fileRepository.wasModifiedBetweenCommits(metricTree1.getName(), commit1Time, commit2Time, projectId));
                child.setChanges(changes);
                deltaTree.getChildren().add(child);
                i++;
                j++;
            } else if(metricTree1 != null && (j >= commit2Tree.getChildren().size() || metricTree1.getName().compareTo(metricTree2.getName()) < 0 )){
                DeltaTree child = new DeltaTree();
                child.setName(metricTree1.getName());
                child.setType(MetricsTreeNodeType.FILE);
                child.setCommit1Metrics(metricTree1.getMetrics());
                child.setCommit2Metrics(null);
                Changes changes = new Changes();
                changes.setDeleted(true);
                child.setChanges(changes);
                deltaTree.getChildren().add(child);
                removedFiles.add(metricTree1.getName());
                i++;
            } else if(metricTree2 != null && (i >= commit1Tree.getChildren().size() || metricTree1.getName().compareTo(metricTree2.getName()) > 0)){
                DeltaTree child = new DeltaTree();
                child.setName(metricTree2.getName());
                child.setType(MetricsTreeNodeType.FILE);
                child.setCommit1Metrics(null);
                child.setCommit2Metrics(metricTree2.getMetrics());
                Changes changes = new Changes();
                changes.setAdded(true);
                child.setChanges(changes);
                deltaTree.getChildren().add(child);
                addedFiles.add(metricTree2.getName());
                j++;
            }
        }

        for(String addedFile : addedFiles){
            String oldPath = fileRepository.wasRenamedBetweenCommits(addedFile, commit1Time, commit2Time, projectId);
            if(removedFiles.contains(oldPath)){
                DeltaTree oldName = null;
                DeltaTree newName = null;
                for(DeltaTree child : deltaTree.getChildren()){
                    if(child.getName().equals(oldPath)){
                         oldName = child;
                    }else if(child.getName().equals(addedFile)){
                        newName = child;
                    }
                }
                if(oldName != null && newName != null){
                    newName.setCommit1Metrics(oldName.getCommit1Metrics());
                    newName.getChanges().setAdded(false);
                    newName.getChanges().setRenamed(true);
                    newName.setRenamedFrom(oldPath);
                    newName.setRenamedTo(addedFile);
                    deltaTree.getChildren().remove(oldName);
                }
            }
        }
        return deltaTree;
    }
}
