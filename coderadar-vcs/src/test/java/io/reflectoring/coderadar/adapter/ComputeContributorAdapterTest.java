package io.reflectoring.coderadar.adapter;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.adapter.ComputeContributorAdapter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.assertj.core.api.Assertions;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ComputeContributorAdapterTest {
  private URL testRepoURL = this.getClass().getClassLoader().getResource("test-repository");
  @TempDir public File folder;

  @BeforeEach
  public void setup() throws GitAPIException {
    Git git = Git.cloneRepository().setURI(testRepoURL.toString()).setDirectory(folder).call();
    git.close();
  }

  @Test
  void test() {
    ComputeContributorAdapter computeContributorAdapter = new ComputeContributorAdapter();

    List<Contributor> contributors =
        computeContributorAdapter.computeContributors(
            folder.getAbsolutePath(),
            new ArrayList<>(),
            new DateRange().setStartDate(LocalDate.MIN).setEndDate(LocalDate.MAX));

    Assertions.assertThat(contributors.size()).isEqualTo(2);
    Assertions.assertThat(contributors.get(0).getDisplayName()).isEqualTo("Krause");
    Assertions.assertThat(contributors.get(0).getNames()).containsExactly("Krause");
    Assertions.assertThat(contributors.get(0).getEmailAddresses())
        .containsExactly("kilian.krause@adesso.de");

    Assertions.assertThat(contributors.get(1).getDisplayName()).isEqualTo("maximAtanasov");
    Assertions.assertThat(contributors.get(1).getNames()).containsExactly("maximAtanasov");
    Assertions.assertThat(contributors.get(1).getEmailAddresses())
        .containsExactly("maksim.atanasov@adesso.de");
  }

  @Test
  void contributorsNotCommitedInDateRangeAreNotAddedTest() {
    ComputeContributorAdapter computeContributorAdapter = new ComputeContributorAdapter();
    List<Contributor> contributors =
        computeContributorAdapter.computeContributors(
            folder.getAbsolutePath(),
            new ArrayList<>(),
            new DateRange()
                .setStartDate(LocalDate.of(2019, 8, 5))
                .setEndDate(LocalDate.of(2019, 8, 12)));
    Assertions.assertThat(contributors.get(0).getDisplayName()).isEqualTo("maximAtanasov");
    Assertions.assertThat(contributors.size()).isEqualTo(1);
  }

  @AfterEach
  public void tearDown() throws IOException {
    FileUtils.deleteDirectory(folder);
  }
}
