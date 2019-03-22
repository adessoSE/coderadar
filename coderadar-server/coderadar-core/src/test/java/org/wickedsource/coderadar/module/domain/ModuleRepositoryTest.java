package org.wickedsource.coderadar.module.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Modules.SINGLE_PROJECT_WITH_MODULES;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.commit.domain.CommitToFileAssociation;
import org.wickedsource.coderadar.projectadministration.domain.Module;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

public class ModuleRepositoryTest extends IntegrationTestTemplate {

  @Autowired private ModuleRepository moduleRepository;

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_MODULES)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_MODULES)
  public void findFilesByModulePath() {
    List<CommitToFileAssociation> files = moduleRepository.findFilesByModulePath("server/analyzer");
    assertThat(files).hasSize(2);
    assertThat(files.get(0).getSourceFile().getFilepath())
        .isEqualTo("server/analyzer/src/main/java/fileOnlyTouchedInFirstCommit.java");
    assertThat(files.get(1).getSourceFile().getFilepath())
        .isEqualTo("server/analyzer/submodule/testfile");
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_MODULES)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_MODULES)
  public void findModulesForFile() {
    List<Module> modules = moduleRepository.findModulesForFile("server/analyzer/submodule/newfile");
    assertThat(modules).hasSize(2);
    assertThat(modules.get(0).getPath()).isEqualTo("server/analyzer");
    assertThat(modules.get(1).getPath()).isEqualTo("server/analyzer/submodule");
  }
}
