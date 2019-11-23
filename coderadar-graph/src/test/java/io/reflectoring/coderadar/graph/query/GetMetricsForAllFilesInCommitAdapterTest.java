package io.reflectoring.coderadar.graph.query;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.query.adapter.GetMetricTreeForCommitAdapter;
import io.reflectoring.coderadar.graph.query.repository.GetMetricValuesOfCommitRepository;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Get metrics for all files in commit")
class GetMetricsForAllFilesInCommitAdapterTest {
  private GetMetricValuesOfCommitRepository getMetricsForAllFilesInCommitRepository =
      mock(GetMetricValuesOfCommitRepository.class);
  private GetMetricValuesOfCommitRepository getMetricValuesOfCommitRepository =
      mock(GetMetricValuesOfCommitRepository.class);
  private ProjectRepository projectRepository = mock(ProjectRepository.class);
  private ModuleRepository moduleRepository = mock(ModuleRepository.class);
  private CommitRepository commitRepository = mock(CommitRepository.class);

  private GetMetricTreeForCommitAdapter getMetricsForAllFilesInCommitAdapter;

  /*  @Test TODO: This should be an integration test
  @DisplayName("Should return list of GroupedMetricValueDTO when passing a valid argument")
  void shouldReturnListOfGroupedMetricValueDTOWhenPassingAValidArgument() {
    getMetricsForAllFilesInCommitAdapter =
        new GetMetricsForAllFilesInCommitAdapter(
            getMetricsForAllFilesInCommitRepository,
            projectRepository,
            moduleRepository,
            commitRepository);


    when(getProjectRepository.findById(anyLong())).thenReturn(java.util.Optional.of(new ProjectEntity()));

    GetMetricsForCommitCommand command =
        new GetMetricsForCommitCommand("1A", new ArrayList<>());
    MetricTree result = getMetricsForAllFilesInCommitAdapter.get(command, 1L);
    Assertions.assertThat(result).isNotNull();
  }*/
}
