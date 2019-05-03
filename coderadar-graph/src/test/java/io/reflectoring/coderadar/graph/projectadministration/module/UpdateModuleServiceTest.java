package io.reflectoring.coderadar.graph.projectadministration.module;

import io.reflectoring.coderadar.core.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.GetModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.UpdateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.UpdateModuleService;
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
@DisplayName("Update module")
 class UpdateModuleServiceTest {
    @Mock
    private UpdateModuleRepository updateModuleRepository;

    @Mock
    private GetModuleRepository getModuleRepository;

    @InjectMocks
    private UpdateModuleService updateModuleService;

    @Test
    @DisplayName("Should throw exception when a module with the passing ID doesn't exists")
     void shouldThrowExceptionWhenAModuleWithThePassingIdDoesntExists() {
        Assertions.assertThrows(
                ModuleNotFoundException.class, () -> updateModuleService.updateModule(new Module()));
    }

    @Test
    @DisplayName("Should update project when a module with the passing ID exists")
     void shouldUpdateProjectWhenAModuleWithThePassingIdExists() {
        Module mockedOldItem = new Module();
        mockedOldItem.setId(1L);
        mockedOldItem.setPath("/dev/null");
        when(getModuleRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedOldItem));

        Module mockedItem = new Module();
        mockedItem.setId(1L);
        mockedItem.setPath("/opt");
        when(updateModuleRepository.save(any(Module.class))).thenReturn(mockedItem);

        Module project = new Module();
        project.setId(1L);
        project.setPath("/opt");
        updateModuleService.updateModule(project);

        verify(updateModuleRepository, times(1)).save(mockedItem);
        Assertions.assertNotEquals(mockedOldItem, project);
    }
}
