package org.wickedsource.coderadar.commit.rest;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Commits.SINGLE_PROJECT_WITH_COMMITS;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.PagedResources;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

public class CommitControllerTest extends ControllerTestTemplate {

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_COMMITS)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_COMMITS)
  public void getCommits() throws Exception {
    ConstrainedFields fields = fields(CommitResource.class);
    mvc()
        .perform(get("/projects/1/commits?page=3&size=5"))
        .andExpect(status().isOk())
        .andExpect(containsResource(PagedResources.class))
        .andDo(
            document(
                "commit/list",
                linksInPath(
                    "$._embedded.commitResourceList[0]",
                    linkWithRel("project")
                        .description("The project resource this commit belongs to.")),
                responseFieldsInPath(
                    "$._embedded.commitResourceList[0]",
                    fields.withPath("name").description("The name of the commit."),
                    fields.withPath("author").description("The author (committer) of the commit."),
                    fields
                        .withPath("timestamp")
                        .description("The timestamp of the commit in milliseconds since epoch."),
                    fields
                        .withPath("analyzed")
                        .description(
                            "Whether this commit has already been analyzed by the source code analyzers or not. If it has been analyzed, code metrics for this commit should be available."))));
  }
}
