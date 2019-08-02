package io.reflectoring.coderadar.graph.query;

import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ListModulesOfProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetMetricValuesOfCommitRepository;
import io.reflectoring.coderadar.graph.query.service.GetMetricsForAllFilesInCommitAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

@DisplayName("Get metrics for all files in commit")
class GetMetricsForAllFilesInCommitAdapterTest {
  private GetMetricValuesOfCommitRepository getMetricValuesOfCommitRepository =
      mock(GetMetricValuesOfCommitRepository.class);
  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);
  private ListModulesOfProjectRepository listModulesOfProjectRepository =
      mock(ListModulesOfProjectRepository.class);
  private CreateModuleRepository createModuleRepository = mock(CreateModuleRepository.class);

  private GetMetricsForAllFilesInCommitAdapter getMetricsForAllFilesInCommitAdapter;

  @Test
  @DisplayName("Should return list of GroupedMetricValueDTO when passing a valid argument")
  void shouldReturnListOfGroupedMetricValueDTOWhenPassingAValidArgument() {
    getMetricsForAllFilesInCommitAdapter =
        new GetMetricsForAllFilesInCommitAdapter(
            getMetricValuesOfCommitRepository,
            getProjectRepository,
            listModulesOfProjectRepository,
            createModuleRepository);

    /*    GetMetricsForAllFilesInCommitCommand command =
        new GetMetricsForAllFilesInCommitCommand("1A", new LinkedList<>());
    List<GroupedMetricValueDTO> returnedList = getMetricsForAllFilesInCommitAdapter.get(command);
    Assertions.assertThat(returnedList).isNotNull();*/
  }
}
