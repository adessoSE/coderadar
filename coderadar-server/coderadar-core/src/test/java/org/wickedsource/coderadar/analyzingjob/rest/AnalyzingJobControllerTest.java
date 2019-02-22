package org.wickedsource.coderadar.analyzingjob.rest;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.AnalyzingStrategies.SINGLE_PROJECT_WITH_ANALYZING_STRATEGY;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.EMPTY;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Projects.SINGLE_PROJECT;
import static org.wickedsource.coderadar.factories.resources.ResourceFactory.analyzingJobResource;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.http.MediaType;
import org.wickedsource.coderadar.testframework.category.ControllerTest;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

@Category(ControllerTest.class)
public class AnalyzingJobControllerTest extends ControllerTestTemplate {

  @Test
  @DatabaseSetup(SINGLE_PROJECT)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_ANALYZING_STRATEGY)
  public void setAnalyzingJob() throws Exception {
    AnalyzingJobResource resource = analyzingJobResource().analyzingJobResource();

    ConstrainedFields fields = fields(AnalyzingJobResource.class);
    mvc()
        .perform(
            post("/projects/1/analyzingJob")
                .content(toJsonWithoutLinks(resource))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(containsResource(AnalyzingJobResource.class))
        .andDo(
            document(
                "analyzing-job/create-update",
                requestFields(
                    fields
                        .withPath("active")
                        .description(
                            "Defines if this AnalyzingJob is active. If it is not active (or there is no AnalyzingJob for the project at all), no commits of the project will be analyzed. Analyses of commits that are already queued will be finished, however."),
                    fields
                        .withPath("rescan")
                        .description(
                            "Set this to true when setting the AnalyzingJob for a project if you want to delete all existing analysis results and restart analysis with the new AnalyzingJob. You will get an error response if you try rescanning a project while there are running or queued analysis jobs (i.e. when the previous scan over all commits is not yet finished)."),
                    fields
                        .withPath("fromDate")
                        .description(
                            "Date (in milliseconds since epoch) from which to start analyzing commits. Commits before this date are ignored during analysis. If no date is specified, all commits will be analyzed."))));
  }

  @Test
  @DatabaseSetup(EMPTY)
  @ExpectedDatabase(EMPTY)
  public void setAnalyzingJobProjectNotFound() throws Exception {
    AnalyzingJobResource resource = analyzingJobResource().analyzingJobResource();
    mvc()
        .perform(
            post("/projects/1/analyzingJob")
                .content(toJsonWithoutLinks(resource))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_ANALYZING_STRATEGY)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_ANALYZING_STRATEGY)
  public void getAnalyzingJob() throws Exception {
    mvc()
        .perform(get("/projects/1/analyzingJob"))
        .andExpect(status().isOk())
        .andExpect(containsResource(AnalyzingJobResource.class))
        .andDo(document("analyzing-job/get"));
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT)
  @ExpectedDatabase(SINGLE_PROJECT)
  public void getNonExistingAnalyzingJob() throws Exception {
    mvc().perform(get("/projects/1/analyzingJob")).andExpect(status().isNotFound());
  }

  @Test
  @DatabaseSetup(EMPTY)
  @ExpectedDatabase(EMPTY)
  public void getAnalyzingJobProjectNotFound() throws Exception {
    mvc().perform(get("/projects/1/analyzingJob")).andExpect(status().isNotFound());
  }
}
