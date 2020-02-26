package io.reflectoring.coderadar.graph.contributor;

import io.reflectoring.coderadar.contributor.ContributorNotFoundException;
import io.reflectoring.coderadar.graph.contributor.adapter.GetContributorAdapter;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetContributorAdapterTest {
    private GetContributorAdapter getContributorAdapter;

    @Mock
    private ContributorRepository contributorRepository;

    @BeforeEach
    public void setup() {
        getContributorAdapter = new GetContributorAdapter(contributorRepository);
    }

    @Test
    public void throwsExceptionIfContributorDoesNotExist() {
        when(contributorRepository.findById(10L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ContributorNotFoundException.class, () -> getContributorAdapter.findById(10L));
    }
}
