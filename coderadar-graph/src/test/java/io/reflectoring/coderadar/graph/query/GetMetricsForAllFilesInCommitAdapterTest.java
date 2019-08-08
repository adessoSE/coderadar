package io.reflectoring.coderadar.graph.query;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ListModulesOfProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetCommitsInProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetMetricValuesOfCommitRepository;
import io.reflectoring.coderadar.graph.query.service.GetMetricsForAllFilesInCommitAdapter;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Get metrics for all files in commit")
class GetMetricsForAllFilesInCommitAdapterTest {
  private GetMetricValuesOfCommitRepository getMetricsForAllFilesInCommitRepository =
      mock(GetMetricValuesOfCommitRepository.class);
  private GetMetricValuesOfCommitRepository getMetricValuesOfCommitRepository =
      mock(GetMetricValuesOfCommitRepository.class);
  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);
  private ListModulesOfProjectRepository listModulesOfProjectRepository =
      mock(ListModulesOfProjectRepository.class);
  private CreateModuleRepository createModuleRepository = mock(CreateModuleRepository.class);
  private GetCommitsInProjectRepository getCommitsInProjectRepository =
      mock(GetCommitsInProjectRepository.class);

  private GetMetricsForAllFilesInCommitAdapter getMetricsForAllFilesInCommitAdapter;

  /*  @Test TODO: This should be an integration test
  @DisplayName("Should return list of GroupedMetricValueDTO when passing a valid argument")
  void shouldReturnListOfGroupedMetricValueDTOWhenPassingAValidArgument() {
    getMetricsForAllFilesInCommitAdapter =
        new GetMetricsForAllFilesInCommitAdapter(
            getMetricsForAllFilesInCommitRepository,
            getProjectRepository,
            listModulesOfProjectRepository,
            createModuleRepository,
            getCommitsInProjectRepository);


    when(getProjectRepository.findById(anyLong())).thenReturn(java.util.Optional.of(new ProjectEntity()));

    GetMetricsForCommitCommand command =
        new GetMetricsForCommitCommand("1A", new ArrayList<>());
    MetricTree result = getMetricsForAllFilesInCommitAdapter.get(command, 1L);
    Assertions.assertThat(result).isNotNull();
  }*/
}
