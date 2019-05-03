package io.reflectoring.coderadar.graph.projectadministration.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.GetModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.GetModuleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Get module")
class GetModuleServiceTest {
    @Mock
    private GetModuleRepository getModuleRepository;

    @InjectMocks
    private GetModuleService getModuleService;

    @Test
    @DisplayName("Should return module as optional when a module with the passing ID exists")
    void shouldReturnModuleAsOptionalWhenAModuleWithThePassingIdExists() {
        Module mockedItem = new Module();
        mockedItem.setId(1L);
        when(getModuleRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedItem));

        Optional<Module> returned = getModuleService.get(1L);

        verify(getModuleRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(getModuleRepository);
        Assertions.assertTrue(returned.isPresent());
        Assertions.assertEquals(new Long(1L), returned.get().getId());
    }

    @Test
    @DisplayName("Should return module as empty optional when a module with the passing ID doesn't exists")
    void shouldReturnModuleAsEmptyOptionalWhenAModuleWithThePassingIdDoesntExists() {
        Optional<Module> mockedItem = Optional.empty();
        when(getModuleRepository.findById(any(Long.class))).thenReturn(mockedItem);

        Optional<Module> returned = getModuleService.get(1L);

        verify(getModuleRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(getModuleRepository);
        Assertions.assertFalse(returned.isPresent());
    }
}
