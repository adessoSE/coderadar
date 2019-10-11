package io.reflectoring.coderadar.rest.query;

import com.fasterxml.jackson.core.type.TypeReference;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.ErrorMessageResponse;
import io.reflectoring.coderadar.rest.IdResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetAvailableMetricsInProjectControllerTest extends ControllerTestTemplate {

    private Long projectId;

    @BeforeEach
    void setUp() throws Exception {
        URL testRepoURL =  this.getClass().getClassLoader().getResource("test-repository");
        CreateProjectCommand command1 =
                new CreateProjectCommand(
                        "test-project", "username", "password", Objects.requireNonNull(testRepoURL).toString(), false, null, null);
        MvcResult result = mvc().perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command1))).andReturn();

        projectId = fromJson(result.getResponse().getContentAsString(), IdResponse.class).getId();

        CreateFilePatternCommand command2 =
                new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
        mvc()
                .perform(
                        post("/projects/" + projectId + "/filePatterns")
                                .content(toJson(command2))
                                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void returnsNothingWhenNotAnalyzed() throws Exception {
        MvcResult result = mvc().perform(get("/projects/" + projectId + "/metrics").contentType(MediaType.APPLICATION_JSON)).andReturn();

        List<String> metrics = fromJson(new TypeReference<List<String>>() {}, result.getResponse().getContentAsString());

        Assertions.assertTrue(metrics.isEmpty());
    }

    @Test
    void returnsLocMetrics() throws Exception {
        CreateAnalyzerConfigurationCommand command3 = new CreateAnalyzerConfigurationCommand("io.reflectoring.coderadar.analyzer.loc.LocAnalyzerPlugin", true);
        mvc().perform(post("/projects/" + projectId + "/analyzers").content(toJson(command3)).contentType(MediaType.APPLICATION_JSON));

        StartAnalyzingCommand command4 = new StartAnalyzingCommand(new Date(), true);
        mvc().perform(post("/projects/" + projectId + "/analyze").content(toJson(command4)).contentType(MediaType.APPLICATION_JSON));

        MvcResult result = mvc().perform(get("/projects/" + projectId + "/metrics").contentType(MediaType.APPLICATION_JSON)).andReturn();

        List<String> metrics = fromJson(new TypeReference<List<String>>() {}, result.getResponse().getContentAsString());

        Assertions.assertEquals(4, metrics.size());
        Assertions.assertTrue(metrics.contains("coderadar:size:eloc:java"));
        Assertions.assertTrue(metrics.contains("coderadar:size:sloc:java"));
        Assertions.assertTrue(metrics.contains("coderadar:size:cloc:java"));
        Assertions.assertTrue(metrics.contains("coderadar:size:loc:java"));
    }

    @Test
    void returnsCheckstyleMetrics() throws Exception {
        CreateAnalyzerConfigurationCommand command3 = new CreateAnalyzerConfigurationCommand("io.reflectoring.coderadar.analyzer.checkstyle.CheckstyleSourceCodeFileAnalyzerPlugin", true);
        mvc().perform(post("/projects/" + projectId + "/analyzers").content(toJson(command3)).contentType(MediaType.APPLICATION_JSON));

        StartAnalyzingCommand command4 = new StartAnalyzingCommand(new Date(), true);
        mvc().perform(post("/projects/" + projectId + "/analyze").content(toJson(command4)).contentType(MediaType.APPLICATION_JSON));

        MvcResult result = mvc().perform(get("/projects/" + projectId + "/metrics").contentType(MediaType.APPLICATION_JSON)).andReturn();

        List<String> metrics = fromJson(new TypeReference<List<String>>() {}, result.getResponse().getContentAsString());

        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.PackageDeclarationCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.OuterTypeFilenameCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocPackageCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.regexp.RegexpCheck"));
    }

    @Test
    void returnsLocAndCheckstyleMetrics() throws Exception {
        CreateAnalyzerConfigurationCommand createAnalyzerConfigurationCommand = new CreateAnalyzerConfigurationCommand("io.reflectoring.coderadar.analyzer.checkstyle.CheckstyleSourceCodeFileAnalyzerPlugin", true);
        mvc().perform(post("/projects/" + projectId + "/analyzers").content(toJson(createAnalyzerConfigurationCommand)).contentType(MediaType.APPLICATION_JSON));

        CreateAnalyzerConfigurationCommand createAnalyzerConfigurationCommand1 = new CreateAnalyzerConfigurationCommand("io.reflectoring.coderadar.analyzer.loc.LocAnalyzerPlugin", true);
        mvc().perform(post("/projects/" + projectId + "/analyzers").content(toJson(createAnalyzerConfigurationCommand1)).contentType(MediaType.APPLICATION_JSON));

        StartAnalyzingCommand startAnalyzingCommand = new StartAnalyzingCommand(new Date(), true);
        mvc().perform(post("/projects/" + projectId + "/analyze").content(toJson(startAnalyzingCommand)).contentType(MediaType.APPLICATION_JSON));

        MvcResult result = mvc().perform(get("/projects/" + projectId + "/metrics").contentType(MediaType.APPLICATION_JSON)).andReturn();

        List<String> metrics = fromJson(new TypeReference<List<String>>() {}, result.getResponse().getContentAsString());

        Assertions.assertTrue(metrics.contains("coderadar:size:eloc:java"));
        Assertions.assertTrue(metrics.contains("coderadar:size:sloc:java"));
        Assertions.assertTrue(metrics.contains("coderadar:size:cloc:java"));
        Assertions.assertTrue(metrics.contains("coderadar:size:loc:java"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.PackageDeclarationCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.OuterTypeFilenameCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocPackageCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck"));
        Assertions.assertTrue(metrics.contains("checkstyle:com.puppycrawl.tools.checkstyle.checks.regexp.RegexpCheck"));
    }

    @Test
    void returnsErrorWhenProjectWithIdDoesNotExist() throws Exception {
        MvcResult result = mvc().perform(get("/projects/1234/metrics").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorMessageResponse response = fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class);

        Assertions.assertEquals("Project with id 1234 not found.", response.getErrorMessage());
    }
}
