package io.reflectoring.coderadar;

import io.reflectoring.coderadar.plugin.api.ChangeType;
import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;
import io.reflectoring.coderadar.vcs.port.driven.CloneRepositoryPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.reflectoring.coderadar.dependencyMap.domain.CompareNode;
import io.reflectoring.coderadar.dependencyMap.domain.CompareNodeDTO;
import io.reflectoring.coderadar.analyzer.levelizedStructureMap.domain.DependencyCompareTree;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CompareTreeTest {

    private String repository;
    private String commitName;
    private String commitName2;
    private CompareNode root;
    private File f;


    @Mock
    private CloneRepositoryPort cloneRepositoryPort;
    @Mock
    private DependencyCompareTree dependencyTree;
//    private CloneRepositoryPort cloneRepositoryPort = mock(CloneRepositoryPort.class);
//    private DependencyCompareTree dependencyTree = mock(DependencyCompareTree.class);

    @BeforeEach
    public void initEach() {
        try {
            f = new File(this.getClass().getClassLoader().getResource("").getPath(), "testSrc");
            cloneRepositoryPort.cloneRepository("https://github.com/jo2/testSrc.git", f);
            // TODO how to get repository info from here
            commitName = "44f0adc62806ecbb34cb4a6fa2d9c24d6a85b0e1";
            commitName2 = "d823c8d85b6f28e67ec21b832dd19f1687125041";
            // second commit contains:
            //    - add dependency on existing file - done (WildcardImport2Test -> NotADependencyTest)
            //    - add file - done (RandomCLass)
            //    - add dependency on new file - done (WildcardImport2Test -> RandomCLass2)
            //    - move a file - done (FullyClassifiedDependencyTest -> extras.FullyClassifiedDependencyTest)
            //    - add dependency on moved file - done (WildcardImport2Test -> FullyClassifiedDependencyTest)
            //    - remove dependency on existing file - done (CoreTest -> NotADependencyTest)
            //    - remove dependency on removed file - done (WildcardImport1Test)
            //    - rename a file - done (WildcardImportCircularDependencyTest => WildcardImportCircularDependency)
            //    - add a dependency on a renamed file - done (CoreTest -> WildcardImportCircularDependency)
            //    - remove a dependency on a renamed file - done (DuplicateDependencies2Test -> WildcardImportCircularDependencyTest)
            //    - change a files content - done (DuplicateDependenciesTest)
            root = dependencyTree.getRoot(repository, commitName, "testSrc", commitName2);
        } catch (UnableToCloneRepositoryException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void cleanUpEach() {
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
    public void testTraversePre() {
        StringBuilder traversed = new StringBuilder();
        root.traversePre(node -> traversed.append(node.getFilename()).append("\n"));
        String expected = "testSrc\n" +
                "src\n" +
                "org\n" +
                "wickedsource\n" +
                "dependencytree\n" +
                "wildcardpackage\n" +
                "WildcardImportCircularDependencyTest.java\n" +
                "WildcardImportCircularDependency.java\n" +
                "WildcardImport2Test.java\n" +
                "WildcardImport1Test.java\n" +
                "somepackage\n" +
                "RandomClass2.java\n" +
                "RandomClass.java\n" +
                "NotADependencyTest.java\n" +
                "InvalidDependencyTest.java\n" +
                "FullyClassifiedDependencyTest.java\n" +
                "DuplicateDependenciesTest.java\n" +
                "DuplicateDependencies2Test.java\n" +
                "CoreDependencyTest.java\n" +
                "CircularDependencyTest.java\n" +
                "extras\n" +
                "FullyClassifiedDependencyTest.java\n" +
                "CoreTest.java\n";
        Assertions.assertNotNull(traversed.toString());
        Assertions.assertEquals(expected, traversed.toString());
    }

    @Test
    public void testTraversePost() {
        StringBuilder traversed = new StringBuilder();
        root.traversePost(node -> traversed.append(node.getFilename()).append("\n"));
        String expected = "WildcardImportCircularDependencyTest.java\n" +
                "WildcardImportCircularDependency.java\n" +
                "WildcardImport2Test.java\n" +
                "WildcardImport1Test.java\n" +
                "wildcardpackage\n" +
                "RandomClass2.java\n" +
                "RandomClass.java\n" +
                "NotADependencyTest.java\n" +
                "InvalidDependencyTest.java\n" +
                "FullyClassifiedDependencyTest.java\n" +
                "DuplicateDependenciesTest.java\n" +
                "DuplicateDependencies2Test.java\n" +
                "CoreDependencyTest.java\n" +
                "CircularDependencyTest.java\n" +
                "FullyClassifiedDependencyTest.java\n" +
                "extras\n" +
                "somepackage\n" +
                "CoreTest.java\n" +
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
        CompareNode dependencytree = root.getChildByName("src").getChildByName("org").getChildByName("wickedsource").getChildByName("dependencytree");
        Assertions.assertEquals(3, dependencytree.getChildren().size());

        List<CompareNodeDTO> dtos;
        // all children of dependencytree
        {
            Assertions.assertEquals("CoreTest.java", dependencytree.getChildren().get(0).getFilename());
            Assertions.assertEquals("somepackage", dependencytree.getChildren().get(1).getFilename());
            Assertions.assertEquals("wildcardpackage", dependencytree.getChildren().get(2).getFilename());

            CompareNode coreTest = dependencytree.getChildByName("CoreTest.java");
            dtos = new ArrayList<>();
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport1Test.java", ChangeType.DELETE));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java", null));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependency.java", ChangeType.ADD));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependencyTest.java", ChangeType.DELETE));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/somepackage/FullyClassifiedDependencyTest.java", ChangeType.DELETE));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/somepackage/extras/FullyClassifiedDependencyTest.java", ChangeType.ADD));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/somepackage/NotADependencyTest.java", ChangeType.DELETE));
            assertHasDependencies(coreTest, dtos, ChangeType.MODIFY);

            CompareNode wildcardPackage = dependencytree.getChildByName("wildcardpackage");
            Assertions.assertTrue(coreTest.getLevel() < wildcardPackage.getLevel());
            Assertions.assertEquals(4, wildcardPackage.getChildren().size());

            dtos = new ArrayList<>();
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/somepackage/NotADependencyTest.java", ChangeType.ADD));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/somepackage/RandomClass2.java", ChangeType.ADD));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/somepackage/extras/FullyClassifiedDependencyTest.java", ChangeType.ADD));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/somepackage/FullyClassifiedDependencyTest.java", ChangeType.ADD));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/CoreTest.java", ChangeType.DELETE));
            assertHasDependencies(wildcardPackage, dtos, null);

            // all children of wildcardpackage
            {
                CompareNode wildcardImport1Test = wildcardPackage.getChildByName("WildcardImport1Test.java");
                Assertions.assertNotNull(wildcardImport1Test);
                Assertions.assertEquals(ChangeType.DELETE, wildcardImport1Test.getChanged());
                dtos = new ArrayList<>();
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/CoreTest.java", ChangeType.DELETE));
                assertHasDependencies(wildcardImport1Test, dtos, ChangeType.DELETE);

                dtos = new ArrayList<>();
                // TODO dependency auf classe A wurde hinzugefügt, aber A ist nicht mehr A sondern sth/A (unter git ADD und DELETE), sth/A hinzugefügt ist klar, aber wie ist der Status der dependency auf A?
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/somepackage/NotADependencyTest.java", ChangeType.ADD));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/somepackage/RandomClass2.java", ChangeType.ADD));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/somepackage/extras/FullyClassifiedDependencyTest.java", ChangeType.ADD));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/somepackage/FullyClassifiedDependencyTest.java", ChangeType.ADD));
                assertHasDependencies(wildcardPackage.getChildByName("WildcardImport2Test.java"), dtos, ChangeType.MODIFY);

                assertLeafWithoutDependencies(wildcardPackage.getChildByName("WildcardImportCircularDependency.java"), ChangeType.ADD);
                assertLeafWithoutDependencies(wildcardPackage.getChildByName("WildcardImportCircularDependencyTest.java"), ChangeType.DELETE);
            }

            CompareNode somepackage = dependencytree.getChildByName("somepackage");
            Assertions.assertEquals(10, somepackage.getChildren().size());

            dtos = new ArrayList<>();
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java", null));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport1Test.java", ChangeType.DELETE));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependency.java", ChangeType.DELETE));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependencyTest.java", ChangeType.DELETE));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java", null));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport1Test.java", ChangeType.DELETE));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependency.java", ChangeType.ADD));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependencyTest.java", ChangeType.DELETE));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/CoreTest.java", null));
            assertHasDependencies(somepackage, dtos, null);

            // all children of somepackage
            {
                CompareNode extras = somepackage.getChildByName("extras");
                Assertions.assertNotNull(extras);
                Assertions.assertEquals(1, extras.getChildren().size());

                // all children of extras
                assertLeafWithoutDependencies(extras.getChildByName("FullyClassifiedDependencyTest.java"), ChangeType.ADD);

                assertLeafWithoutDependencies(somepackage.getChildByName("CircularDependencyTest.java"), null);
                assertLeafWithoutDependencies(somepackage.getChildByName("CoreDependencyTest.java"), null);

                dtos = new ArrayList<>();
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java", null));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport1Test.java", ChangeType.DELETE));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependency.java", ChangeType.DELETE));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependencyTest.java", ChangeType.DELETE));
                assertHasDependencies(somepackage.getChildByName("DuplicateDependencies2Test.java"), dtos, ChangeType.MODIFY);

                dtos = new ArrayList<>();
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java", null));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport1Test.java", ChangeType.DELETE));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependency.java", ChangeType.ADD));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependencyTest.java", ChangeType.DELETE));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/CoreTest.java", null));
                assertHasDependencies(somepackage.getChildByName("DuplicateDependenciesTest.java"), dtos, null);

                assertLeafWithoutDependencies(somepackage.getChildByName("InvalidDependencyTest.java"), null);
                assertLeafWithoutDependencies(somepackage.getChildByName("NotADependencyTest.java"), null);
                assertLeafWithoutDependencies(somepackage.getChildByName("RandomClass.java"), ChangeType.ADD);
                assertLeafWithoutDependencies(somepackage.getChildByName("RandomClass2.java"), ChangeType.ADD);
                assertLeafWithoutDependencies(somepackage.getChildByName("FullyClassifiedDependencyTest.java"), ChangeType.DELETE);
            }
        }
    }

    @Test
    public void testHasDependencyOn() {
        CompareNode wildcardpackage = root.getNodeByPath("src/org/wickedsource/dependencytree/wildcardpackage");
        CompareNode somepackage = root.getNodeByPath("src/org/wickedsource/dependencytree/somepackage");
        CompareNode coreTest = root.getNodeByPath("src/org/wickedsource/dependencytree/CoreTest.java");

        Assertions.assertTrue(somepackage.hasDependencyOn(coreTest));
        Assertions.assertTrue(coreTest.hasDependencyOn(somepackage));
        Assertions.assertTrue(wildcardpackage.hasDependencyOn(coreTest));
        Assertions.assertTrue(coreTest.hasDependencyOn(wildcardpackage));
        Assertions.assertTrue(wildcardpackage.hasDependencyOn(somepackage));
        Assertions.assertTrue(somepackage.hasDependencyOn(wildcardpackage));
    }

    @Test
    public void testCountDependenciesOn() {
        CompareNode wildcardpackage = root.getNodeByPath("src/org/wickedsource/dependencytree/wildcardpackage");
        CompareNode somepackage = root.getNodeByPath("src/org/wickedsource/dependencytree/somepackage");
        CompareNode coreTest = root.getNodeByPath("src/org/wickedsource/dependencytree/CoreTest.java");

        Assertions.assertEquals(1, somepackage.countDependenciesOn(coreTest));
        Assertions.assertEquals(3, coreTest.countDependenciesOn(somepackage));
        Assertions.assertEquals(1, wildcardpackage.countDependenciesOn(coreTest));
        Assertions.assertEquals(4, coreTest.countDependenciesOn(wildcardpackage));
        Assertions.assertEquals(4, wildcardpackage.countDependenciesOn(somepackage));
        somepackage.getChildByName("DuplicateDependencies2Test.java").getDependencies().stream()
                .filter(dependency -> !dependency.getPath().endsWith("WildcardImport2Test.java"))
                .forEach(dependency -> Assertions.assertEquals(ChangeType.DELETE, dependency.getChanged()));
        Assertions.assertEquals(8, somepackage.countDependenciesOn(wildcardpackage));
    }

    @Test
    public void testGetNodeFromImportNoClass() {
        List<CompareNode> imports = dependencyTree.getNodeFromImport("org.wickedsource.dependencytree.sbdfjksbdf");
        Assertions.assertEquals(0, imports.size());
    }

    @Test
    public void testGetNodeFromImportWildcard() {
        List<CompareNode> imports = dependencyTree.getNodeFromImport("org.wickedsource.dependencytree.wildcardpackage.*");
        Assertions.assertEquals(4, imports.size());
    }

    @Test
    public void testGetNodeFromImportClass() {
        List<CompareNode> imports = dependencyTree.getNodeFromImport("org.wickedsource.dependencytree.somepackage.CoreDependencyTest");
        Assertions.assertEquals(1, imports.size());
    }

    @Test
    public void testGetParent() {
        CompareNode somepackage = root.getNodeByPath("src/org/wickedsource/dependencytree/somepackage");
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

    private void assertLeafWithoutDependencies(CompareNode node, ChangeType changed) {
        Assertions.assertNotNull(node);
        Assertions.assertTrue(node.getChildren().isEmpty());
        Assertions.assertTrue(node.getDependencies().isEmpty());
        Assertions.assertEquals(changed, node.getChanged());

    }

    private void assertHasDependencies(CompareNode node, List<CompareNodeDTO> dependencies, ChangeType changed) {
        Assertions.assertNotNull(node);
        Assertions.assertEquals(changed, node.getChanged());
        Assertions.assertEquals(dependencies.size(), node.getDependencies().size());
        for (CompareNodeDTO dto : dependencies) {
            Assertions.assertNotNull(node.getDependencyByPath(dto.getPath()));
            boolean found = false;
            for (CompareNodeDTO tmp : node.getDependencies()) {
                if (dto.getPath().equals(tmp.getPath())) {
                    if (dto.getChanged() != null) {
                        if (dto.getChanged().equals(tmp.getChanged())) {
                            found = true;
                        }
                    } else if (tmp.getChanged() == null) {
                        found = true;
                    }
                }
            }
            Assertions.assertTrue(found);
        }
    }
}
