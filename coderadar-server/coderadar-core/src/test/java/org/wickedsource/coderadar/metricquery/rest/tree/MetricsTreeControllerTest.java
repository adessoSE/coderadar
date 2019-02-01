package org.wickedsource.coderadar.metricquery.rest.tree;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Modules.SINGLE_PROJECT_WITH_METRICS_AND_MODULES;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.fromJson;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MvcResult;
import org.wickedsource.coderadar.metricquery.rest.tree.delta.DeltaTreePayload;
import org.wickedsource.coderadar.metricquery.rest.tree.delta.DeltaTreeQuery;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

public class MetricsTreeControllerTest extends ControllerTestTemplate {

	@Test
	@DatabaseSetup(SINGLE_PROJECT_WITH_METRICS_AND_MODULES)
	public void queryMetricsTree() throws Exception {

		MetricsTreeQuery query = new MetricsTreeQuery();
		query.setCommit("eb7cbd429530dc26d06a9ea2a62794421dce1e9a");
		query.addMetrics("metric1", "metric2", "metric3");

		ConstrainedFields requestFields = fields(MetricsTreeQuery.class);

		MvcResult result =
				mvc()
						.perform(
								get("/projects/1/metricvalues/tree")
										.content(toJsonWithoutLinks(query))
										.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(
								containsResource(new TypeReference<MetricsTreeResource<CommitMetricsPayload>>() {}))
						.andDo(
								document(
										"metrics/tree",
										requestFields(
												requestFields
														.withPath("commit")
														.description(
																"Name of the commit that defines the point in time at which the metrics should be queried."),
												requestFields
														.withPath("metrics")
														.description(
																"List of the names of the metrics whose values you want to query.")),
										responseFields(
												fieldWithPath("name")
														.type(JsonFieldType.STRING)
														.description(
																"The name of the file or module, containing the full path."),
												fieldWithPath("type")
														.type(JsonFieldType.STRING)
														.description(
																"Either 'MODULE' if this node describes a module which can have child nodes or 'FILE' if this node describes a file (which has no child nodes)."),
												fieldWithPath("metrics")
														.type(JsonFieldType.OBJECT)
														.description(
																"Contains a map of metric values for each of the metrics specified in the query at the time of the commit specified in the request. If this node is a MODULE, the metrics are aggregated over all files within this module."),
												fieldWithPath("children")
														.type(JsonFieldType.ARRAY)
														.description(
																"If this node describes a MODULE, this field contains the list of child nodes of the same structure, which can be of type MODULE or FILE."))))
						.andReturn();

		MetricsTreeResource<CommitMetricsPayload> metricsTreeResource =
				fromJson(
						result.getResponse().getContentAsString(),
						new TypeReference<MetricsTreeResource<CommitMetricsPayload>>() {});
		assertThat(metricsTreeResource.getChildren()).hasSize(2);
		assertThat(metricsTreeResource.getPayload().getMetrics().getMetricValue("metric1"))
				.isEqualTo(26L + 12L);
		assertThat(metricsTreeResource.getPayload().getMetrics().getMetricValue("metric2"))
				.isEqualTo(28L + 0L);
		assertThat(metricsTreeResource.getPayload().getMetrics().getMetricValue("metric3"))
				.isEqualTo(14L + 5L);
		assertThat(metricsTreeResource.getChildren().get(0).getName()).isEqualTo("/path/to/module1");
		assertThat(metricsTreeResource.getChildren().get(0).getChildren()).hasSize(2);
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(0)
								.getPayload()
								.getMetrics()
								.getMetricValue("metric1"))
				.isEqualTo(26L);
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(0)
								.getPayload()
								.getMetrics()
								.getMetricValue("metric2"))
				.isEqualTo(28L);
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(0)
								.getPayload()
								.getMetrics()
								.getMetricValue("metric3"))
				.isEqualTo(14L);
		assertThat(metricsTreeResource.getChildren().get(0).getChildren().get(0).getName())
				.isEqualTo("/path/to/module1/src/main/java/test.java");
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(0)
								.getChildren()
								.get(0)
								.getPayload()
								.getMetrics()
								.getMetricValue("metric1"))
				.isEqualTo(12L);
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(0)
								.getChildren()
								.get(0)
								.getPayload()
								.getMetrics()
								.getMetricValue("metric2"))
				.isEqualTo(13L);
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(0)
								.getChildren()
								.get(0)
								.getPayload()
								.getMetrics()
								.getMetricValue("metric3"))
				.isEqualTo(11L);
		assertThat(metricsTreeResource.getChildren().get(0).getChildren().get(0).getChildren())
				.isEmpty();
		assertThat(metricsTreeResource.getChildren().get(1).getName()).isEqualTo("/path/to/module3");
		assertThat(metricsTreeResource.getChildren().get(1).getChildren()).hasSize(1);
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(1)
								.getPayload()
								.getMetrics()
								.getMetricValue("metric1"))
				.isEqualTo(12L);
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(1)
								.getPayload()
								.getMetrics()
								.getMetricValue("metric2"))
				.isNull();
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(1)
								.getPayload()
								.getMetrics()
								.getMetricValue("metric3"))
				.isEqualTo(5L);
	}

	@Test
	@DatabaseSetup(SINGLE_PROJECT_WITH_METRICS_AND_MODULES)
	public void queryDeltaTree() throws Exception {

		DeltaTreeQuery query = new DeltaTreeQuery();
		query.setCommit1("eb7cbd429530dc26d06a9ea2a62794421dce1e9a");
		query.setCommit2("eb7cbd429530dc26d06a9ea2a62794421dce1e9a");
		query.addMetrics("metric1", "metric2", "metric3");

		ConstrainedFields requestFields = fields(DeltaTreeQuery.class);

		MvcResult result =
				mvc()
						.perform(
								get("/projects/1/metricvalues/deltaTree")
										.content(toJsonWithoutLinks(query))
										.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(
								containsResource(new TypeReference<MetricsTreeResource<DeltaTreePayload>>() {}))
						.andDo(
								document(
										"metrics/deltaTree",
										requestFields(
												requestFields
														.withPath("commit1")
														.description(
																"Name of the first commit whose metric values should be queried."),
												requestFields
														.withPath("commit2")
														.description(
																"Name of the second commit whose metric values should be queried. This commit's timestamp must be later than the first commit's timestamp!"),
												requestFields
														.withPath("metrics")
														.description(
																"List of the names of the metrics whose values you want to query.")),
										responseFields(
												fieldWithPath("name")
														.type(JsonFieldType.STRING)
														.description(
																"The name of the file or module, containing the full path."),
												fieldWithPath("type")
														.type(JsonFieldType.STRING)
														.description(
																"Either 'MODULE' if this node describes a module which can have child nodes or 'FILE' if this node describes a file (which has no child nodes)."),
												fieldWithPath("commit1Metrics")
														.type(JsonFieldType.OBJECT)
														.description(
																"Contains a map of metric values for each of the metrics specified in the query at the time of the commit specified as 'commit1' in the request. This field is NULL if the file or module has not existed yet in commit1. If this node is a MODULE, the metrics are aggregated over all files within this module."),
												fieldWithPath("commit2Metrics")
														.type(JsonFieldType.OBJECT)
														.description(
																"Contains a map of metric values for each of the metrics specified in the query at the time of the commit specified as 'commit2' in the request. This field is NULL if the file or module does not exist anymore in commit2. If this node is a MODULE, the metrics are aggregated over all files within this module."),
												fieldWithPath("renamedFrom")
														.type(JsonFieldType.VARIES)
														.description(
																"Only contains a value if this file has been renamed (moved) between commit1 and commit2. In this case, this field contains the old name of the file, before it was renamed. Otherwise, this field is NULL."),
												fieldWithPath("renamedTo")
														.type(JsonFieldType.VARIES)
														.description(
																"Only contains a value if this file has been renamed (moved) between commit1 and commit2. In this case, this field contains the new name of the file, after it was renamed. Otherwise, this field is NULL."),
												fieldWithPath("changes")
														.type(JsonFieldType.VARIES)
														.description(
																"Contains information about what kind of changes were made to this file between commit1 and commit2. This field is only filled for FILEs and not for MODULEs."),
												fieldWithPath("children")
														.type(JsonFieldType.ARRAY)
														.description(
																"If this node describes a MODULE, this field contains the list of child nodes of the same structure, which can be of type MODULE or FILE."))))
						.andReturn();

		MetricsTreeResource<DeltaTreePayload> metricsTreeResource =
				fromJson(
						result.getResponse().getContentAsString(),
						new TypeReference<MetricsTreeResource<DeltaTreePayload>>() {});
		assertThat(metricsTreeResource.getChildren()).hasSize(2);
		assertThat(metricsTreeResource.getPayload().getCommit1Metrics().getMetricValue("metric1"))
				.isEqualTo(26L + 12L);
		assertThat(metricsTreeResource.getPayload().getCommit1Metrics().getMetricValue("metric2"))
				.isEqualTo(28L + 0L);
		assertThat(metricsTreeResource.getPayload().getCommit1Metrics().getMetricValue("metric3"))
				.isEqualTo(14L + 5L);
		assertThat(metricsTreeResource.getChildren().get(0).getName()).isEqualTo("/path/to/module1");
		assertThat(metricsTreeResource.getChildren().get(0).getChildren()).hasSize(2);
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(0)
								.getPayload()
								.getCommit1Metrics()
								.getMetricValue("metric1"))
				.isEqualTo(26L);
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(0)
								.getPayload()
								.getCommit1Metrics()
								.getMetricValue("metric2"))
				.isEqualTo(28L);
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(0)
								.getPayload()
								.getCommit1Metrics()
								.getMetricValue("metric3"))
				.isEqualTo(14L);
		assertThat(metricsTreeResource.getChildren().get(0).getChildren().get(0).getName())
				.isEqualTo("/path/to/module1/src/main/java/test.java");
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(0)
								.getChildren()
								.get(0)
								.getPayload()
								.getCommit1Metrics()
								.getMetricValue("metric1"))
				.isEqualTo(12L);
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(0)
								.getChildren()
								.get(0)
								.getPayload()
								.getCommit1Metrics()
								.getMetricValue("metric2"))
				.isEqualTo(13L);
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(0)
								.getChildren()
								.get(0)
								.getPayload()
								.getCommit1Metrics()
								.getMetricValue("metric3"))
				.isEqualTo(11L);
		assertThat(metricsTreeResource.getChildren().get(0).getChildren().get(0).getChildren())
				.isEmpty();
		assertThat(metricsTreeResource.getChildren().get(1).getName()).isEqualTo("/path/to/module3");
		assertThat(metricsTreeResource.getChildren().get(1).getChildren()).hasSize(1);
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(1)
								.getPayload()
								.getCommit1Metrics()
								.getMetricValue("metric1"))
				.isEqualTo(12L);
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(1)
								.getPayload()
								.getCommit1Metrics()
								.getMetricValue("metric2"))
				.isNull();
		assertThat(
						metricsTreeResource
								.getChildren()
								.get(1)
								.getPayload()
								.getCommit1Metrics()
								.getMetricValue("metric3"))
				.isEqualTo(5L);
	}
}
