package org.wickedsource.coderadar.metricquery.rest.commit.profilerating;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.MetricValues.SINGLE_PROJECT_WITH_METRICS_AND_QUALITY_PROFILES;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.fromJson;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;

public class CommitProfileRatingsControllerTest extends ControllerTestTemplate {

    @Test
    @DatabaseSetup(SINGLE_PROJECT_WITH_METRICS_AND_QUALITY_PROFILES)
    @ExpectedDatabase(SINGLE_PROJECT_WITH_METRICS_AND_QUALITY_PROFILES)
    public void queryCommitProfiles() throws Exception {

        CommitProfileRatingsQuery params = new CommitProfileRatingsQuery();
        params.setCommit("eb7cbd429530dc26d06a9ea2a62794421dce1e9a");
        params.addProfiles("profile1", "profile2");

        ConstrainedFields requestFields = fields(CommitProfileRatingsQuery.class);

        MvcResult result = mvc().perform(get("/projects/1/profileratings/perCommit")
                .content(toJsonWithoutLinks(params))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(containsResource(CommitProfileRatingsOutputResource.class))
                .andDo(document("metrics/commit/profiles",
                        requestFields(
                                requestFields.withPath("commit").description("Name of the commit whose metric values to get."),
                                requestFields.withPath("profiles").description("List of names of the quality profiles whose ratings you want to query."))))
                .andReturn();

        CommitProfileRatingsOutputResource output = fromJson(result.getResponse().getContentAsString(), CommitProfileRatingsOutputResource.class);
        assertThat(output.getProfiles()).hasSize(2);
        assertThat(output.getProfiles().get("profile1").getComplianceRating()).isEqualTo(22);
        assertThat(output.getProfiles().get("profile1").getViolationRating()).isEqualTo(20);
        assertThat(output.getProfiles().get("profile2").getComplianceRating()).isEqualTo(18);
        assertThat(output.getProfiles().get("profile2").getViolationRating()).isEqualTo(21);
    }

}