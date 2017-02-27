package org.wickedsource.coderadar.qualityprofile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Projects.SINGLE_PROJECT;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.QualityProfiles.*;
import static org.wickedsource.coderadar.factories.resources.QualityProfileResourceFactory.profile;
import static org.wickedsource.coderadar.factories.resources.QualityProfileResourceFactory.profile2;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.fromJson;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.*;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.wickedsource.coderadar.qualityprofile.domain.MetricDTO;
import org.wickedsource.coderadar.qualityprofile.rest.QualityProfileResource;
import org.wickedsource.coderadar.testframework.category.ControllerTest;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

@Category(ControllerTest.class)
public class QualityProfileControllerTest extends ControllerTestTemplate {

  @Test
  @DatabaseSetup(SINGLE_PROJECT)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_QUALITY_PROFILE)
  public void createQualityProfile() throws Exception {
    ConstrainedFields fields = fields(QualityProfileResource.class);
    QualityProfileResource resource = profile();
    mvc()
        .perform(
            post("/projects/1/qualityprofiles")
                .content(toJsonWithoutLinks(resource))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(containsResource(QualityProfileResource.class))
        .andDo(
            document(
                "qualityprofiles/create",
                links(
                    halLinks(),
                    linkWithRel("self").description("Link to this quality profile."),
                    linkWithRel("project")
                        .description("Link to the project this quality profile belongs to.")),
                requestFields(
                    fields.withPath("profileName").description("The display name of the profile."),
                    fields
                        .withPath("metrics[].metricName")
                        .description("Name of the metric that is part of this quality profile."),
                    fields
                        .withPath("metrics[].metricType")
                        .description(
                            "Either VIOLATION if high metric values should be interpreted as negative or COMPLIANCE if high metric values should be interpreted as positive."))));
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_QUALITY_PROFILES)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_QUALITY_PROFILES)
  public void listQualityProfiles() throws Exception {
    mvc()
        .perform(get("/projects/1/qualityprofiles?page=0&size=2"))
        .andExpect(status().isOk())
        .andExpect(containsPagedResources(QualityProfileResource.class))
        .andDo(document("qualityprofiles/list"));
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_QUALITY_PROFILE)
  @ExpectedDatabase(SINGLE_PROJECT)
  public void deleteQualityProfile() throws Exception {
    mvc()
        .perform(delete("/projects/1/qualityprofiles/50"))
        .andExpect(status().isOk())
        .andDo(document("qualityprofiles/delete"));
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_QUALITY_PROFILE2)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_QUALITY_PROFILE3)
  public void updateQualityProfile() throws Exception {
    QualityProfileResource resource = profile2();
    mvc()
        .perform(
            post("/projects/1/qualityprofiles/50")
                .content(toJsonWithoutLinks(resource))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(containsResource(QualityProfileResource.class))
        .andDo(document("qualityprofiles/update"));
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT_WITH_QUALITY_PROFILE)
  @ExpectedDatabase(SINGLE_PROJECT_WITH_QUALITY_PROFILE)
  public void getQualityProfile() throws Exception {
    QualityProfileResource resource = profile();
    MvcResult result =
        mvc()
            .perform(
                get("/projects/1/qualityprofiles/50")
                    .content(toJsonWithoutLinks(resource))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(containsResource(QualityProfileResource.class))
            .andDo(document("qualityprofiles/get"))
            .andReturn();

    QualityProfileResource profile =
        fromJson(result.getResponse().getContentAsString(), QualityProfileResource.class);
    assertThat(profile.getProfileName()).isEqualTo(resource.getProfileName());
    assertThat(profile.getMetrics().size()).isEqualTo(resource.getMetrics().size());
    List<String> sentMetrics =
        resource
            .getMetrics()
            .stream()
            .map(MetricDTO::getMetricName)
            .sorted()
            .collect(Collectors.toList());
    List<String> returnedMetrics =
        profile
            .getMetrics()
            .stream()
            .map(MetricDTO::getMetricName)
            .sorted()
            .collect(Collectors.toList());
    assertThat(sentMetrics).isEqualTo(returnedMetrics);
  }
}
