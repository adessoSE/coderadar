package io.reflectoring.coderadar.rest.dependencymap;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.dependencymap.adapter.DependencyTreeAdapter;
import io.reflectoring.coderadar.dependencymap.domain.Node;
import io.reflectoring.coderadar.dependencymap.domain.NodeDTO;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectUseCase;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.vcs.port.driven.DeleteLocalRepositoryPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class TreeTest extends ControllerTestTemplate {

  private Node root;
  private File f;
  private static final Logger logger = LoggerFactory.getLogger(TreeTest.class);

  @Autowired private DependencyTreeAdapter dependencyTree;
  @Autowired private DeleteLocalRepositoryPort deleteRepositoryPort;
  @Autowired private CoderadarConfigurationProperties coderadarConfigurationProperties;
  @Autowired private CreateProjectUseCase createProjectUseCase;
  @Autowired private GetProjectPort getProjectPort;

  @BeforeEach
  public void initEach() {
    try {
      URL testRepoURL = this.getClass().getClassLoader().getResource("testSrc");
      CreateProjectCommand command = new CreateProjectCommand();
      command.setVcsUrl(testRepoURL.toString());
      command.setName("testSrc");
      command.setVcsOnline(true);
      Project testProject = getProjectPort.get(createProjectUseCase.createProject(command));
      String commitName = "d026b5e9ad0ff034a137711e4faa47322f014fbb";

      f =
          new File(
              coderadarConfigurationProperties.getWorkdir()
                  + "/projects/"
                  + testProject.getWorkdirName());

      mvc()
          .perform(get("/api/analyzers/" + testProject.getId() + "/structureMap/" + commitName))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(containsResource(Node.class))
          .andDo(result -> root = fromJson(result.getResponse().getContentAsString(), Node.class));
      Assertions.assertNotNull(root);
    } catch (Exception e) {
      logger.error("Error getting dependency tree: " + e.getMessage());
    }
  }

  @AfterEach
  public void cleanUpEach() {
    try {
      deleteRepositoryPort.deleteRepository(f.getPath());
      deleteFile(f);
    } catch (IOException e) {
      logger.error("Problems with deleting test repository after tests.");
    }
  }

  private void deleteFile(File f) {
    File[] allContents = f.listFiles();
    if (allContents != null) {
      Arrays.stream(allContents).forEach(this::deleteFile);
    }
    f.delete();
  }

  @Test
  public void testInPackageDependencies() {
    Assertions.assertNotNull(root);
    Node notADependencyTest =
        root.getNodeByPath(
            "src/org/wickedsource/dependencytree/somepackage/NotADependencyTest.java");
    Assertions.assertNotNull(notADependencyTest);
    Assertions.assertEquals(1, notADependencyTest.getDependencies().size());
  }

  @Test
  public void testCreateTree() {
    Assertions.assertNotNull(root);
    Assertions.assertEquals("testSrc", root.getFilename());
    Assertions.assertEquals(1, root.getChildren().size());
    Node dependencytreeNode =
        root.getChildByName("src")
            .getChildByName("org")
            .getChildByName("wickedsource")
            .getChildByName("dependencytree");
    Assertions.assertEquals(3, dependencytreeNode.getChildren().size());

    List<NodeDTO> dtos;
    // all children of dependencytree
    {
      dependencytreeNode.getChildren().sort(Comparator.comparing(Node::getFilename));
      Assertions.assertEquals(
          "CoreTest.java", dependencytreeNode.getChildren().get(0).getFilename());
      Assertions.assertEquals("somepackage", dependencytreeNode.getChildren().get(1).getFilename());
      Assertions.assertEquals(
          "wildcardpackage", dependencytreeNode.getChildren().get(2).getFilename());

      Node coreTest = dependencytreeNode.getChildByName("CoreTest.java");
      dtos = new ArrayList<>();
      dtos.add(
          new NodeDTO(
              "src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java"));
      dtos.add(
          new NodeDTO(
              "src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependency.java"));
      dtos.add(
          new NodeDTO(
              "src/org/wickedsource/dependencytree/somepackage/extras/FullyClassifiedDependencyTest.java"));
      assertHasDependencies(coreTest, dtos);

      Node wildcardPackage = dependencytreeNode.getChildByName("wildcardpackage");
      Assertions.assertTrue(coreTest.getLevel() < wildcardPackage.getLevel());
      Assertions.assertEquals(2, wildcardPackage.getChildren().size());

      dtos = new ArrayList<>();
      dtos.add(
          new NodeDTO("src/org/wickedsource/dependencytree/somepackage/NotADependencyTest.java"));
      dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/somepackage/RandomClass2.java"));
      dtos.add(
          new NodeDTO(
              "src/org/wickedsource/dependencytree/somepackage/extras/FullyClassifiedDependencyTest.java"));
      assertHasDependencies(wildcardPackage, dtos);

      // all children of wildcardpackage
      {
        dtos = new ArrayList<>();
        dtos.add(
            new NodeDTO("src/org/wickedsource/dependencytree/somepackage/NotADependencyTest.java"));
        dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/somepackage/RandomClass2.java"));
        dtos.add(
            new NodeDTO(
                "src/org/wickedsource/dependencytree/somepackage/extras/FullyClassifiedDependencyTest.java"));
        assertHasDependencies(wildcardPackage.getChildByName("WildcardImport2Test.java"), dtos);
        assertLeafWithoutDependencies(
            wildcardPackage.getChildByName("WildcardImportCircularDependency.java"));
      }

      Node somepackage = dependencytreeNode.getChildByName("somepackage");
      Assertions.assertEquals(9, somepackage.getChildren().size());

      dtos = new ArrayList<>();
      dtos.add(
          new NodeDTO(
              "src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java"));
      dtos.add(
          new NodeDTO(
              "src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java"));
      dtos.add(
          new NodeDTO(
              "src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependency.java"));
      dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/CoreTest.java"));
      dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/somepackage/RandomClass2.java"));
      assertHasDependencies(somepackage, dtos);

      // all children of somepackage
      {
        Node extras = somepackage.getChildByName("extras");
        Assertions.assertEquals(9, somepackage.getChildren().size());
        // all children of extras
        {
          assertLeafWithoutDependencies(
              extras.getChildByName("FullyClassifiedDependencyTest.java"));
        }

        assertLeafWithoutDependencies(somepackage.getChildByName("CircularDependencyTest.java"));
        assertLeafWithoutDependencies(somepackage.getChildByName("CoreDependencyTest.java"));

        dtos = new ArrayList<>();
        dtos.add(
            new NodeDTO(
                "src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java"));
        assertHasDependencies(somepackage.getChildByName("DuplicateDependencies2Test.java"), dtos);

        dtos = new ArrayList<>();
        dtos.add(
            new NodeDTO(
                "src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java"));
        dtos.add(
            new NodeDTO(
                "src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependency.java"));
        dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/CoreTest.java"));
        assertHasDependencies(somepackage.getChildByName("DuplicateDependenciesTest.java"), dtos);

        assertLeafWithoutDependencies(somepackage.getChildByName("InvalidDependencyTest.java"));

        dtos = new ArrayList<>();
        dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/somepackage/RandomClass2.java"));
        assertHasDependencies(somepackage.getChildByName("NotADependencyTest.java"), dtos);

        assertLeafWithoutDependencies(somepackage.getChildByName("RandomClass.java"));
        assertLeafWithoutDependencies(somepackage.getChildByName("RandomClass2.java"));
      }
    }
  }

  @Test
  public void testHasDependencyOn() {
    Assertions.assertNotNull(root);
    Node wildcardpackage =
        root.getNodeByPath("src/org/wickedsource/dependencytree/wildcardpackage");
    Node somepackage = root.getNodeByPath("src/org/wickedsource/dependencytree/somepackage");
    Node coreTest = root.getNodeByPath("src/org/wickedsource/dependencytree/CoreTest.java");

    Assertions.assertTrue(somepackage.hasDependencyOn(coreTest));
    Assertions.assertTrue(coreTest.hasDependencyOn(somepackage));
    Assertions.assertFalse(wildcardpackage.hasDependencyOn(coreTest));
    Assertions.assertTrue(coreTest.hasDependencyOn(wildcardpackage));
    Assertions.assertTrue(wildcardpackage.hasDependencyOn(somepackage));
    Assertions.assertTrue(somepackage.hasDependencyOn(wildcardpackage));
  }

  @Test
  public void testCountDependenciesOn() {
    Assertions.assertNotNull(root);
    Node wildcardpackage =
        root.getNodeByPath("src/org/wickedsource/dependencytree/wildcardpackage");
    Node somepackage = root.getNodeByPath("src/org/wickedsource/dependencytree/somepackage");
    Node coreTest = root.getNodeByPath("src/org/wickedsource/dependencytree/CoreTest.java");

    Assertions.assertEquals(1, somepackage.countDependenciesOn(coreTest));
    Assertions.assertEquals(1, coreTest.countDependenciesOn(somepackage));
    Assertions.assertEquals(0, wildcardpackage.countDependenciesOn(coreTest));
    Assertions.assertEquals(2, coreTest.countDependenciesOn(wildcardpackage));
    Assertions.assertEquals(3, wildcardpackage.countDependenciesOn(somepackage));
    Assertions.assertEquals(3, somepackage.countDependenciesOn(wildcardpackage));
  }

  @Test
  public void testGetNodeFromImportNoClass() {
    List<Node> imports =
        dependencyTree.getNodeFromImport("org.wickedsource.dependencytree.sbdfjksbdf");
    Assertions.assertEquals(0, imports.size());
  }

  @Test
  public void testGetNodeFromImportWildcard() {
    List<Node> imports =
        dependencyTree.getNodeFromImport("org.wickedsource.dependencytree.wildcardpackage.*");
    Assertions.assertEquals(2, imports.size());
  }

  @Test
  public void testGetNodeFromImportClass() {
    List<Node> imports =
        dependencyTree.getNodeFromImport(
            "org.wickedsource.dependencytree.somepackage.CoreDependencyTest");
    Assertions.assertEquals(1, imports.size());
  }

  private void assertLeafWithoutDependencies(Node node) {
    Assertions.assertNotNull(node);
    Assertions.assertTrue(node.getChildren().isEmpty());
    Assertions.assertTrue(node.getDependencies().isEmpty());
  }

  private void assertHasDependencies(Node node, List<NodeDTO> dependencies) {
    Assertions.assertNotNull(node);
    Assertions.assertEquals(dependencies.size(), node.getDependencies().size());
    for (NodeDTO dto : dependencies) {
      Assertions.assertNotNull(node.getDependencyByPath(dto.getPath()));
    }
  }
}
