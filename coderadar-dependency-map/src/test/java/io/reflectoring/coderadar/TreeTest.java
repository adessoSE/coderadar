package io.reflectoring.coderadar;

import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;
import io.reflectoring.coderadar.vcs.port.driven.CloneRepositoryPort;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import io.reflectoring.coderadar.analyzer.levelizedStructureMap.domain.DependencyTree;
import io.reflectoring.coderadar.dependencyMap.domain.Node;
import io.reflectoring.coderadar.dependencyMap.domain.NodeDTO;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TreeTest {

    private String repository;
    private String commitName;
    private Node root;
    private File f;

    private CloneRepositoryPort cloneRepositoryPort = mock(CloneRepositoryPort.class);
    private DependencyTree dependencyTree = mock(DependencyTree.class);

    @BeforeAll
    public void initEach() {
        try {
            f = new File(this.getClass().getClassLoader().getResource("").getPath(), "testSrc");
            commitName = "d026b5e9ad0ff034a137711e4faa47322f014fbb";
//            commitName = "44f0adc62806ecbb34cb4a6fa2d9c24d6a85b0e1";
            cloneRepositoryPort.cloneRepository("https://github.com/jo2/testSrc.git", f);
            // TODO how to get repository info from here
            root = dependencyTree.getRoot(repository, commitName, "testSrc");
        } catch (UnableToCloneRepositoryException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public void cleanUpEach() {
        // TODO delete repository
        repository.close();
        deleteFile(f);
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
        Node notADependencyTest = root.getNodeByPath("src/org/wickedsource/dependencytree/somepackage/NotADependencyTest.java");
        Assertions.assertNotNull(notADependencyTest);
        Assertions.assertEquals(1, notADependencyTest.getDependencies().size());
    }

    @Test
    public void testTraversePre() {
        StringBuilder traversed = new StringBuilder();
        root.traversePre(node -> traversed.append(node.getFilename()).append("\n"));
        String expected = "testSrc\n" +
                "src\n" +
                "org\n" +
                "wickedsource\n" +
                "dependencytree\n" +
                "wildcardpackage\n" +
                "WildcardImportCircularDependency.java\n" +
                "WildcardImport2Test.java\n" +
                "CoreTest.java\n" +
                "somepackage\n" +
                "RandomClass2.java\n" +
                "RandomClass.java\n" +
                "NotADependencyTest.java\n" +
                "InvalidDependencyTest.java\n" +
                "DuplicateDependenciesTest.java\n" +
                "DuplicateDependencies2Test.java\n" +
                "CoreDependencyTest.java\n" +
                "CircularDependencyTest.java\n" +
                "extras\n" +
                "FullyClassifiedDependencyTest.java\n";
        Assertions.assertNotNull(traversed.toString());
        Assertions.assertEquals(expected, traversed.toString());
    }

    @Test
    public void testTraversePost() {
        StringBuilder traversed = new StringBuilder();
        root.traversePost(node -> traversed.append(node.getFilename()).append("\n"));
        String expected = "WildcardImportCircularDependency.java\n" +
                "WildcardImport2Test.java\n" +
                "wildcardpackage\n" +
                "CoreTest.java\n" +
                "RandomClass2.java\n" +
                "RandomClass.java\n" +
                "NotADependencyTest.java\n" +
                "InvalidDependencyTest.java\n" +
                "DuplicateDependenciesTest.java\n" +
                "DuplicateDependencies2Test.java\n" +
                "CoreDependencyTest.java\n" +
                "CircularDependencyTest.java\n" +
                "FullyClassifiedDependencyTest.java\n" +
                "extras\n" +
                "somepackage\n" +
                "dependencytree\n" +
                "wickedsource\n" +
                "org\n" +
                "src\n" +
                "testSrc\n";
        Assertions.assertNotNull(traversed.toString());
        Assertions.assertEquals(expected, traversed.toString());
    }

    @Test
    public void testCreateTree() {
        Assertions.assertEquals("testSrc", root.getFilename());
        Assertions.assertEquals(1, root.getChildren().size());
        Node dependencytreeNode = root.getChildByName("src").getChildByName("org").getChildByName("wickedsource").getChildByName("dependencytree");
        Assertions.assertEquals(3, dependencytreeNode.getChildren().size());

        List<NodeDTO> dtos;
        // all children of dependencytree
        {
            Assertions.assertEquals("somepackage", dependencytreeNode.getChildren().get(0).getFilename());
            Assertions.assertEquals("CoreTest.java", dependencytreeNode.getChildren().get(1).getFilename());
            Assertions.assertEquals("wildcardpackage", dependencytreeNode.getChildren().get(2).getFilename());

            Node coreTest = dependencytreeNode.getChildByName("CoreTest.java");
            dtos = new ArrayList<>();
            dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java"));
            dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependency.java"));
            dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/somepackage/extras/FullyClassifiedDependencyTest.java"));
            assertHasDependencies(coreTest, dtos);

            Node wildcardPackage = dependencytreeNode.getChildByName("wildcardpackage");
            Assertions.assertTrue(coreTest.getLevel() < wildcardPackage.getLevel());
            Assertions.assertEquals(2, wildcardPackage.getChildren().size());

            dtos = new ArrayList<>();
            dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/somepackage/NotADependencyTest.java"));
            dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/somepackage/RandomClass2.java"));
            dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/somepackage/extras/FullyClassifiedDependencyTest.java"));
            assertHasDependencies(wildcardPackage, dtos);

            // all children of wildcardpackage
            {
                dtos = new ArrayList<>();
                dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/somepackage/NotADependencyTest.java"));
                dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/somepackage/RandomClass2.java"));
                dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/somepackage/extras/FullyClassifiedDependencyTest.java"));
                assertHasDependencies(wildcardPackage.getChildByName("WildcardImport2Test.java"), dtos);
                assertLeafWithoutDependencies(wildcardPackage.getChildByName("WildcardImportCircularDependency.java"));
            }

            Node somepackage = dependencytreeNode.getChildByName("somepackage");
            Assertions.assertEquals(9, somepackage.getChildren().size());

            dtos = new ArrayList<>();
            dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java"));
            dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java"));
            dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependency.java"));
            dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/CoreTest.java"));
            dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/somepackage/RandomClass2.java"));
            assertHasDependencies(somepackage, dtos);

            // all children of somepackage
            {
                Node extras = somepackage.getChildByName("extras");
                Assertions.assertEquals(9, somepackage.getChildren().size());
                // all children of extras
                {
                    assertLeafWithoutDependencies(extras.getChildByName("FullyClassifiedDependencyTest.java"));
                }

                assertLeafWithoutDependencies(somepackage.getChildByName("CircularDependencyTest.java"));
                assertLeafWithoutDependencies(somepackage.getChildByName("CoreDependencyTest.java"));

                dtos = new ArrayList<>();
                dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java"));
                assertHasDependencies(somepackage.getChildByName("DuplicateDependencies2Test.java"), dtos);

                dtos = new ArrayList<>();
                dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java"));
                dtos.add(new NodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependency.java"));
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
        Node wildcardpackage = root.getNodeByPath("src/org/wickedsource/dependencytree/wildcardpackage");
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
        Node wildcardpackage = root.getNodeByPath("src/org/wickedsource/dependencytree/wildcardpackage");
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
        List<Node> imports = dependencyTree.getNodeFromImport("org.wickedsource.dependencytree.sbdfjksbdf");
        Assertions.assertEquals(0, imports.size());
    }

    @Test
    public void testGetNodeFromImportWildcard() {
        List<Node> imports = dependencyTree.getNodeFromImport("org.wickedsource.dependencytree.wildcardpackage.*");
        Assertions.assertEquals(2, imports.size());
    }

    @Test
    public void testGetNodeFromImportClass() {
        List<Node> imports = dependencyTree.getNodeFromImport("org.wickedsource.dependencytree.somepackage.CoreDependencyTest");
        Assertions.assertEquals(1, imports.size());
    }

    @Test
    public void testGetParent() {
        Node somepackage = root.getNodeByPath("src/org/wickedsource/dependencytree/somepackage");
        Assertions.assertEquals("dependencytree", somepackage.getParent(this.root).getFilename());
    }

    @Test
    public void testGetParentRootChild() {
        Assertions.assertEquals("testSrc", root.getNodeByPath("src").getParent(this.root).getFilename());
    }

    @Test
    public void testGetParentRoot() {
        Assertions.assertNull(root.getParent(this.root));
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
