package org.wickedsource.coderadar.analyzer.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MvcResult;
import org.wickedsource.coderadar.analyzer.levelizedStructureMap.CompareNode;
import org.wickedsource.coderadar.analyzer.levelizedStructureMap.Node;
import org.wickedsource.coderadar.job.LocalGitRepositoryManager;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;
import org.wickedsource.coderadar.project.rest.ProjectController;
import org.wickedsource.coderadar.project.rest.ProjectResource;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.EMPTY;
import static org.wickedsource.coderadar.factories.resources.ResourceFactory.projectResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

public class AnalyzerDependencyControllerTest extends ControllerTestTemplate {

    @Autowired
    private ProjectController projectController;
    @Autowired
    private LocalGitRepositoryManager gitRepositoryManager;
    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    private void init() {
        ProjectResource project = projectResource().validLocalProject();
        ProjectResource saved = projectController.createProject(project).getBody();
        Optional<Project> projectOptional = projectRepository.findById(1L);
        assertThat(projectOptional.isPresent()).isTrue();
        gitRepositoryManager.updateLocalGitRepository(projectOptional.get());
    }

    @Test
    @DatabaseSetup(EMPTY)
    public void getDependencyTree() throws Exception {
        MvcResult result = mvc()
                .perform(get("/analyzers/1/structureMap/643a55c23dce1832b5da07816f068896aef854e6"))
                .andExpect(status().isOk())
                .andExpect(containsResource(Node.class))
                .andDo(document("analyzer/dependencyTree",
                        responseFields(
                                fieldWithPath("filename")
                                        .type(JsonFieldType.STRING)
                                        .description("The name of the file this node represents."),
                                fieldWithPath("path")
                                        .type(JsonFieldType.STRING)
                                        .description("The full path of the file this node represents."),
                                fieldWithPath("packageName")
                                        .type(JsonFieldType.STRING)
                                        .description("The packageName of the class this node represents. Empty if this node does not represent a package or .java file."),
                                fieldWithPath("level")
                                        .type(JsonFieldType.NUMBER)
                                        .description("The level of this node. It is used to calculate this node's place in the levelized structure map."),
                                subsectionWithPath("children")
                                        .type(JsonFieldType.ARRAY)
                                        .description("This children of this node. Is empty if this node represents a file. Contains the nodes representing this node's file's subdirectories und files."),
                                subsectionWithPath("dependencies")
                                        .type(JsonFieldType.ARRAY)
                                        .description("The dependencies of this node on other nodes in this project."))))
                .andReturn();

        Node node = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Node.class);
        assertThat(node.getFilename()).isEqualTo("testSrc");
        assertThat(node.getChildren().size()).isEqualTo(1);
        assertThat(node.getChildren().get(0).getFilename()).isEqualTo("org");
    }

    @Test
    @DatabaseSetup(EMPTY)
    public void getCompareTree() throws Exception {
        MvcResult result = mvc()
                .perform(get("/analyzers/1/structureMap/0b79780c8e8c8736a8e0ddafc964fc4446f007f2/643a55c23dce1832b5da07816f068896aef854e6"))
                .andExpect(status().isOk())
                .andExpect(containsResource(CompareNode.class))
                .andDo(document("analyzer/compareTree",
                        responseFields(
                                fieldWithPath("filename")
                                        .type(JsonFieldType.STRING)
                                        .description("The name of the file this node represents."),
                                fieldWithPath("path")
                                        .type(JsonFieldType.STRING)
                                        .description("The full path of the file this node represents."),
                                fieldWithPath("packageName")
                                        .type(JsonFieldType.STRING)
                                        .description("The packageName of the class this node represents. Empty if this node does not represent a package or .java file."),
                                fieldWithPath("level")
                                        .type(JsonFieldType.NUMBER)
                                        .description("The level of this node. It is used to calculate this node's place in the levelized structure map."),
                                fieldWithPath("changed")
                                        .type(JsonFieldType.VARIES)
                                        .description("The DiffEntry's ChangeType for this node regarding the two chosen commits. Is null if there were no changes"),
                                subsectionWithPath("children")
                                        .type(JsonFieldType.ARRAY)
                                        .description("This children of this node. Is empty if this node represents a file. Contains the nodes representing this node's file's subdirectories und files."),
                                subsectionWithPath("dependencies")
                                        .type(JsonFieldType.ARRAY)
                                        .description("The dependencies of this node on other nodes in this project."))))
                .andReturn();

        CompareNode node = new ObjectMapper().readValue(result.getResponse().getContentAsString(), CompareNode.class);
        assertThat(node.getFilename()).isEqualTo("testSrc");
        node = node.getNodeByPath("org/wickedsource/dependencytree");
        assertThat(node).isNotNull();
        assertThat(node.getChildren().size()).isEqualTo(4);
        assertThat(node.getChildren().get(0).getFilename()).isEqualTo("example");
        assertThat(node.getChildren().get(1).getFilename()).isEqualTo("somepackage");
        assertThat(node.getChildren().get(2).getFilename()).isEqualTo("wildcardpackage");
        assertThat(node.getChildren().get(3).getFilename()).isEqualTo("CoreTest.java");
    }
}
