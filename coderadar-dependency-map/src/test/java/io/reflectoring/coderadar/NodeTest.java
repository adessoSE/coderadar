package io.reflectoring.coderadar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import io.reflectoring.coderadar.dependencyMap.domain.Node;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NodeTest {

    private Node testRoot;

    @BeforeAll
    public void init() {
        testRoot = new Node("testRoot", "", "");
        Node testNode1_1 = new Node("testNode1_1", "testRoot", "");
        Node testNode1_2 = new Node("testNode1_2", "testRoot", "");
        Node testNode1_3 = new Node("testNode1_3", "testRoot", "");
        Node testNode1_4 = new Node("testNode1_4", "testRoot", "");
        testRoot.addToChildren(testNode1_1);
        testRoot.addToChildren(testNode1_2);
        testRoot.addToChildren(testNode1_3);
        testRoot.addToChildren(testNode1_4);

        Node testNode2_1 = new Node("testNode2_1", "testRoot/testNode1_1", "");
        Node testNode2_2 = new Node("testNode2_2", "testRoot/testNode1_1", "");
        testNode1_1.addToChildren(testNode2_1);
        testNode1_1.addToChildren(testNode2_2);

        Node testNode2_3 = new Node("testNode2_3", "testRoot/testNode1_2", "");
        Node testNode2_4 = new Node("testNode2_4", "testRoot/testNode1_2", "");
        testNode1_2.addToChildren(testNode2_3);
        testNode1_2.addToChildren(testNode2_4);
    }

    @Test
    public void testGetNodeByPath() {
        Node test = testRoot.getNodeByPath("testNode1_2/testNode2_4");
        Assertions.assertNotNull(test);
        Assertions.assertEquals("testNode2_4", test.getFilename());
    }

    @Test
    public void testGetNodeByPathEmptyString() {
        Node empty = testRoot.getNodeByPath("");
        Assertions.assertNull(empty);
    }

    @Test
    public void testGetNodeByPathInvalidString() {
        Node empty = testRoot.getNodeByPath("jhsbdlfjs");
        Assertions.assertNull(empty);
    }

    @Test
    public void testGetNodeByPathNull() {
        Node empty = testRoot.getNodeByPath(null);
        Assertions.assertNull(empty);
    }

    @Test
    public void testCreateNodeByPath() {
        Node testRoot = new Node("testRoot", "", "");
        testRoot.createNodeByPath("testNode1_5/testNode2_5/testNode3_1");

        Node testNode1_5 = testRoot.getChildByName("testNode1_5");
        Node testNode2_5 = testNode1_5.getChildByName("testNode2_5");
        Node testNode3_1 = testNode2_5.getChildByName("testNode3_1");

        Assertions.assertNotNull(testNode3_1);
        Assertions.assertEquals("testNode3_1", testNode3_1.getFilename());
        Assertions.assertEquals("testNode1_5/testNode2_5/testNode3_1", testNode3_1.getPath());
    }

    @Test
    public void testCreateNodeByPathEmptyString() {
        Node empty = testRoot.createNodeByPath("");
        Assertions.assertNull(empty);
    }

    @Test
    public void testCreateNodeByPathNull() {
        Node empty = testRoot.createNodeByPath(null);
        Assertions.assertNull(empty);
    }
}
