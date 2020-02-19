package io.reflectoring.coderadar.rest.dependencymap;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.dependencymap.adapter.DependencyCompareTreeAdapter;
import io.reflectoring.coderadar.dependencymap.domain.CompareNode;
import io.reflectoring.coderadar.dependencymap.domain.CompareNodeDTO;
import io.reflectoring.coderadar.plugin.api.ChangeType;
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
import java.util.List;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class CompareTreeTest extends ControllerTestTemplate {

    private CompareNode root;
    private File f;

    private final Logger logger = LoggerFactory.getLogger(CompareTreeTest.class);

    @Autowired private DependencyCompareTreeAdapter dependencyTree;
    @Autowired private DeleteLocalRepositoryPort deleteRepositoryPort;
    @Autowired private CoderadarConfigurationProperties coderadarConfigurationProperties;
    @Autowired private CreateProjectUseCase createProjectUseCase;
    @Autowired private GetProjectPort getProjectPort;

    @BeforeEach
    public void initEach() {
        try {
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
            URL testRepoURL =  this.getClass().getClassLoader().getResource("testSrc");
            CreateProjectCommand command = new CreateProjectCommand();
            command.setVcsUrl(testRepoURL.toString());
            command.setName("testSrc");
            command.setVcsOnline(true);
            Project testProject = getProjectPort.get(createProjectUseCase.createProject(command));
            System.out.println(testProject.getId());

            String commitName = "44f0adc62806ecbb34cb4a6fa2d9c24d6a85b0e1";
            String commitName2 = "d823c8d85b6f28e67ec21b832dd19f1687125041";

            f = new File(coderadarConfigurationProperties.getWorkdir() + "/projects/" + testProject.getWorkdirName());

            mvc()
                    .perform(get("/analyzers/" + testProject.getId() + "/structureMap/" + commitName + "/" + commitName2))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(containsResource(CompareNode.class))
                    .andDo(result -> root = fromJson(result.getResponse().getContentAsString(), CompareNode.class));
            Assertions.assertNotNull(root);
        } catch (Exception e) {
            logger.error("Error getting dependency tree: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @AfterEach
    public void cleanUpEach() {
        try {
            deleteRepositoryPort.deleteRepository(f.getPath());
            deleteFile(f);
        } catch (IOException e) {
            logger.error("Problems with deleting test repository after tests: " + e.getMessage());
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
    public void testCreateTree() {
        Assertions.assertNotNull(root);
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
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java", ChangeType.UNCHANGED));
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
            assertHasDependencies(wildcardPackage, dtos, ChangeType.UNCHANGED);

            // all children of wildcardpackage
            {
                CompareNode wildcardImport1Test = wildcardPackage.getChildByName("WildcardImport1Test.java");
                Assertions.assertNotNull(wildcardImport1Test);
                Assertions.assertEquals(ChangeType.DELETE, wildcardImport1Test.getChanged());
                dtos = new ArrayList<>();
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/CoreTest.java", ChangeType.DELETE));
                assertHasDependencies(wildcardImport1Test, dtos, ChangeType.DELETE);

                dtos = new ArrayList<>();
                // TODO dependency auf classe A wurde hinzugefügt, aber A ist nicht mehr A sondern sth/A (unter git ADD und DELETE), sth/A hinzugefügt ist klar, aber wie ist der Status der dependency auf A? bisher add
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
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java", ChangeType.UNCHANGED));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport1Test.java", ChangeType.DELETE));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependency.java", ChangeType.DELETE));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependencyTest.java", ChangeType.DELETE));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java", ChangeType.UNCHANGED));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport1Test.java", ChangeType.DELETE));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependency.java", ChangeType.ADD));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependencyTest.java", ChangeType.DELETE));
            dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/CoreTest.java", ChangeType.UNCHANGED));
            assertHasDependencies(somepackage, dtos, ChangeType.UNCHANGED);

            // all children of somepackage
            {
                CompareNode extras = somepackage.getChildByName("extras");
                Assertions.assertNotNull(extras);
                Assertions.assertEquals(1, extras.getChildren().size());

                // all children of extras
                assertLeafWithoutDependencies(extras.getChildByName("FullyClassifiedDependencyTest.java"), ChangeType.ADD);

                assertLeafWithoutDependencies(somepackage.getChildByName("CircularDependencyTest.java"), ChangeType.UNCHANGED);
                assertLeafWithoutDependencies(somepackage.getChildByName("CoreDependencyTest.java"), ChangeType.UNCHANGED);

                dtos = new ArrayList<>();
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java", ChangeType.UNCHANGED));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport1Test.java", ChangeType.DELETE));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependency.java", ChangeType.DELETE));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependencyTest.java", ChangeType.DELETE));
                assertHasDependencies(somepackage.getChildByName("DuplicateDependencies2Test.java"), dtos, ChangeType.MODIFY);

                dtos = new ArrayList<>();
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport2Test.java", ChangeType.UNCHANGED));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImport1Test.java", ChangeType.DELETE));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependency.java", ChangeType.ADD));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/wildcardpackage/WildcardImportCircularDependencyTest.java", ChangeType.DELETE));
                dtos.add(new CompareNodeDTO("src/org/wickedsource/dependencytree/CoreTest.java", ChangeType.UNCHANGED));
                assertHasDependencies(somepackage.getChildByName("DuplicateDependenciesTest.java"), dtos, ChangeType.UNCHANGED);

                assertLeafWithoutDependencies(somepackage.getChildByName("InvalidDependencyTest.java"), ChangeType.UNCHANGED);
                assertLeafWithoutDependencies(somepackage.getChildByName("NotADependencyTest.java"), ChangeType.UNCHANGED);
                assertLeafWithoutDependencies(somepackage.getChildByName("RandomClass.java"), ChangeType.ADD);
                assertLeafWithoutDependencies(somepackage.getChildByName("RandomClass2.java"), ChangeType.ADD);
                assertLeafWithoutDependencies(somepackage.getChildByName("FullyClassifiedDependencyTest.java"), ChangeType.DELETE);
            }
        }
    }

    @Test
    public void testHasDependencyOn() {
        Assertions.assertNotNull(root);
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
        Assertions.assertNotNull(root);
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
