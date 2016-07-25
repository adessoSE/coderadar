package org.wickedsource.coderadar.analyzer.rest.analyzerregistry;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.hateoas.PagedResources;
import org.wickedsource.coderadar.testframework.category.ControllerTest;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.EMPTY;

@Category(ControllerTest.class)
public class AnalyzerControllerTest extends ControllerTestTemplate {

    @Test
    @DatabaseSetup(EMPTY)
    @ExpectedDatabase(EMPTY)
    public void listAnalyzers() throws Exception {
        mvc().perform(get("/analyzers?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(contains(PagedResources.class))
                .andDo(document("analyzer/list"));
    }

}