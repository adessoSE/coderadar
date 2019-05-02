package io.reflectoring.coderadar.graph.analyzer;

import io.reflectoring.coderadar.core.analyzer.domain.AnalyzingJob;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetAnalyzingStatusServiceTest {
    @Mock
    private GetAnalyzingStatusRepository getAnalyzingStatusRepository;

    @InjectMocks
    private GetAnalyzingStatusService getanalyzingStatusService;

    @Test
    public void withAnalyzingJobIdShouldReturnAnalyzingJobEntityAsOptional() {
        AnalyzingJob mockedItem = new AnalyzingJob();
        mockedItem.setId(1L);
        when(getAnalyzingStatusRepository.findByProject_Id(any(Long.class))).thenReturn(Optional.of(mockedItem));

        Optional<AnalyzingJob> returned = getanalyzingStatusService.get(1L);

        verify(getAnalyzingStatusRepository, times(1)).findByProject_Id(1L);
        verifyNoMoreInteractions(getAnalyzingStatusRepository);
        Assertions.assertTrue(returned.isPresent());
        Assertions.assertEquals(new Long(1L), returned.get().getId());
    }

    @Test
    public void withNoPersistedAnalyzingJobShouldReturnEmptyOptional() {
        Optional<AnalyzingJob> mockedItem = Optional.empty();
        when(getAnalyzingStatusRepository.findByProject_Id(any(Long.class))).thenReturn(mockedItem);

        Optional<AnalyzingJob> returned = getanalyzingStatusService.get(1L);

        verify(getAnalyzingStatusRepository, times(1)).findByProject_Id(1L);
        verifyNoMoreInteractions(getAnalyzingStatusRepository);
        Assertions.assertFalse(returned.isPresent());
    }
}
