package org.wickedsource.coderadar.commit.rest;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.hateoas.PagedResources;
import org.wickedsource.coderadar.testframework.category.ControllerTest;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Commits.SINGLE_PROJECT_WITH_COMMITS;

@Category(ControllerTest.class)
public class CommitControllerTest extends ControllerTestTemplate {

    @Test
    @DatabaseSetup(SINGLE_PROJECT_WITH_COMMITS)
    @ExpectedDatabase(SINGLE_PROJECT_WITH_COMMITS)
    public void getCommits() throws Exception {
        mvc().perform(get("/projects/1/commits"))
                .andExpect(status().isOk())
                .andExpect(contains(PagedResources.class))
                .andDo(document("commit/list",
                        links(halLinks(),
                                linkWithRel("self").description("Link to the current page of commits."),
                                linkWithRel("next").description("Link to the next page of commits."),
                                linkWithRel("last").description("Link to the last page of commits."),
                                linkWithRel("first").description("Link to the first page of commits."))));
    }

}