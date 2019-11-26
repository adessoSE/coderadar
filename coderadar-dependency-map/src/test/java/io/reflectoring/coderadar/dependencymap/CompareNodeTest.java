package io.reflectoring.coderadar.dependencymap;

import io.reflectoring.coderadar.dependencymap.domain.CompareNode;
import io.reflectoring.coderadar.plugin.api.ChangeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class CompareNodeTest {

    private CompareNode testRoot;

    @BeforeAll
    public void init() {
        testRoot = new CompareNode("testRoot", "", "", ChangeType.UNCHANGED);
        CompareNode testNode1_1 = new CompareNode("testNode1_1", "testNode1_1", "", ChangeType.UNCHANGED);
        CompareNode testNode1_2 = new CompareNode("testNode1_2", "testNode1_2", "", ChangeType.UNCHANGED);
        CompareNode testNode1_3 = new CompareNode("testNode1_3", "testNode1_3", "", ChangeType.UNCHANGED);
        CompareNode testNode1_4 = new CompareNode("testNode1_4", "testNode1_4", "", ChangeType.UNCHANGED);
        testRoot.addToChildren(testNode1_1);
        testRoot.addToChildren(testNode1_2);
        testRoot.addToChildren(testNode1_3);
        testRoot.addToChildren(testNode1_4);

        CompareNode testNode2_1 = new CompareNode("testNode2_1", "testNode1_1/testNode2_1", "", ChangeType.UNCHANGED);
        CompareNode testNode2_2 = new CompareNode("testNode2_2", "testNode1_1/testNode2_2", "", ChangeType.UNCHANGED);
        testNode1_1.addToChildren(testNode2_1);
        testNode1_1.addToChildren(testNode2_2);

        CompareNode testNode2_3 = new CompareNode("testNode2_3", "testNode1_2/testNode2_3", "", ChangeType.UNCHANGED);
        CompareNode testNode2_4 = new CompareNode("testNode2_4", "testNode1_2/testNode2_4", "", ChangeType.UNCHANGED);
        testNode1_2.addToChildren(testNode2_3);
        testNode1_2.addToChildren(testNode2_4);
    }

    @Test
    public void testGetNodeByPath() {
        CompareNode test = testRoot.getNodeByPath("testNode1_2/testNode2_4");
        Assertions.assertNotNull(test);
        Assertions.assertEquals("testNode2_4", test.getFilename());
    }

    @Test
    public void testGetNodeByPathEmptyString() {
        CompareNode empty = testRoot.getNodeByPath("");
        Assertions.assertNull(empty);
    }

    @Test
    public void testGetNodeByPathInvalidString() {
        CompareNode empty = testRoot.getNodeByPath("jhsbdlfjs");
        Assertions.assertNull(empty);
    }

    @Test
    public void testGetNodeByPathNull() {
        CompareNode empty = testRoot.getNodeByPath(null);
        Assertions.assertNull(empty);
    }

    @Test
    public void testCreateNodeByPath() {
        CompareNode testRoot = new CompareNode("testRoot", "", "", ChangeType.UNCHANGED);
        testRoot.createNodeByPath("testNode1_5/testNode2_5/testNode3_1", ChangeType.UNCHANGED);

        CompareNode testNode1_5 = testRoot.getChildByName("testNode1_5");
        CompareNode testNode2_5 = testNode1_5.getChildByName("testNode2_5");
        CompareNode testNode3_1 = testNode2_5.getChildByName("testNode3_1");

        Assertions.assertNotNull(testNode3_1);
        Assertions.assertEquals("testNode3_1", testNode3_1.getFilename());
        Assertions.assertEquals("testNode1_5/testNode2_5/testNode3_1", testNode3_1.getPath());
    }

    @Test
    public void testCreateNodeByPathEmptyString() {
        CompareNode empty = testRoot.createNodeByPath("", ChangeType.UNCHANGED);
        Assertions.assertNull(empty);
    }

    @Test
    public void testCreateNodeByPathNull() {
        CompareNode empty = testRoot.createNodeByPath(null, ChangeType.UNCHANGED);
        Assertions.assertNull(empty);
    }

    @Test
    public void testTraversePre() {
        Assertions.assertNotNull(testRoot);
        StringBuilder traversed = new StringBuilder();
        testRoot.traversePre(node -> traversed.append(node.getFilename()).append("\n"));
        String expected =
                "testRoot\n" +
                        "testNode1_4\n" +
                        "testNode1_3\n" +
                        "testNode1_2\n" +
                        "testNode2_4\n" +
                        "testNode2_3\n" +
                        "testNode1_1\n" +
                        "testNode2_2\n" +
                        "testNode2_1\n";
        Assertions.assertNotNull(traversed.toString());
        Assertions.assertEquals(expected, traversed.toString());
    }

    @Test
    public void testTraversePost() {
        Assertions.assertNotNull(testRoot);
        StringBuilder traversed = new StringBuilder();
        testRoot.traversePost(node -> traversed.append(node.getFilename()).append("\n"));
        String expected =
                "testNode1_4\n" +
                        "testNode1_3\n" +
                        "testNode2_4\n" +
                        "testNode2_3\n" +
                        "testNode1_2\n" +
                        "testNode2_2\n" +
                        "testNode2_1\n" +
                        "testNode1_1\n" +
                        "testRoot\n";
        Assertions.assertNotNull(traversed.toString());
        Assertions.assertEquals(expected, traversed.toString());
    }

    @Test
    public void testGetParent() {
        Assertions.assertNotNull(testRoot);
        CompareNode testNode2_1 = testRoot.getNodeByPath("testNode1_1/testNode2_1");
        System.out.println(testNode2_1.getPath());
        Assertions.assertNotNull(testNode2_1.getParent(testRoot));
        Assertions.assertEquals("testNode1_1", testNode2_1.getParent(testRoot).getFilename());
    }

    @Test
    public void testGetParentRootChild() {
        Assertions.assertNotNull(testRoot);
        Assertions.assertEquals("testRoot", testRoot.getNodeByPath("testNode1_1").getParent(testRoot).getFilename());
    }

    @Test
    public void testGetParentRoot() {
        Assertions.assertNotNull(testRoot);
        Assertions.assertNull(testRoot.getParent(testRoot));
    }
}
