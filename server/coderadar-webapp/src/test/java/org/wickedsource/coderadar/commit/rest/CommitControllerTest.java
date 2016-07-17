package org.wickedsource.coderadar.commit.rest;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.hateoas.PagedResources;
import org.wickedsource.coderadar.ControllerTest;
import org.wickedsource.coderadar.ControllerTestTemplate;

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
                .andExpect(contains(PagedResources.class));
    }

}