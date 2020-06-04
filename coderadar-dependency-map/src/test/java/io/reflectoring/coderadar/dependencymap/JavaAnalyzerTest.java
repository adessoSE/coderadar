package io.reflectoring.coderadar.dependencymap;

import io.reflectoring.coderadar.dependencymap.analyzers.JavaAnalyzer;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JavaAnalyzerTest {

  private final String fileContent =
      "package org.wickedsource.dependencytree.example;\n"
          + "\n"
          + "//These imports shouldn't matter to the DependencyTree\n"
          + "import java.util.Objects;\n"
          + // valid
          "import java.io.BufferedReader;\n"
          + // valid
          "import org.junit.jupiter.api.*;\n"
          + // valid
          "import org.junit.jupiter.api.*;\n"
          + // valid but ignored
          "import org.wickedsource.dependencytree.sbdfjksbdf; import org.wickedsource.dependencytree.fhzgkobd;\n"
          + // valid x2
          "import org.wickedsource/dependencytree.sbdfjksbdf;\n"
          + // invalid
          "import org.wickedsource.3dependencytree.sbdfjksbdf;\n"
          + // invalid
          "//import org.wickedsource.dependencytree.example.somepackage.CoreDependencyTest1;\n"
          + // invalid
          "/*import org.wickedsource.dependencytree.example.somepackage.CoreDependencyTest2;\n"
          + // invalid
          "*import org.wickedsource.dependencytree.example.somepackage.CoreDependencyTest3;\n"
          + // invalid
          "\"import org.wickedsource.dependencytree.example.somepackage.CoreDependencyTest4;\"\n"
          + // invalid
          "import org.wickedsource.dependencytree.example.somepackage.CoreDependencyTest5;\n"
          + // valid
          "import org.wickedsource.dependencytree.example.wildcardpackage.*;\n"
          + // valid
          "\n"
          + "\n"
          + "//Test code for dependencyTree\n"
          + "public class CoreTest<Typ1, Typ2> extends ClassA implements InterfaceB, interfaceC {\n"
          + // valid x6
          "    @Annotation1 RandomClass3 randomClass3;\n"
          + // valid x2
          "    private RandomClass2 randomClass2; RandomClass randomClass;\n"
          + // valid x2
          "\n"
          + "    org.wickedsource.dependencytree.example.somepackage.FullyClassifiedDependencyTest fullyClassifiedDependencyTest;\n"
          + // valid
          "\n"
          + "    public CoreTest() {\n"
          + // valid
          "        ClassB b = new ClassC();\n"
          + // valid x2
          "        ClassD b = ClassE.create();\n"
          + // valid x2
          "        ClassF b = ClassF1.create(ClassG.create(), classH.create());\n"
          + // valid x4
          "        ClassI b = new ClassJ(ClassK.create(), classL.create());\n"
          + // valid x4
          "        ClassM b = (ClassN) ClassO.create();\n"
          + // valid x3
          "        ClassP b = ((ClassQ.isEmpty() && classR.isEmpty()) || classS.isEmpty || ((classT.size > classU.size) && classV.size < classW.size)) ? ClassX.get : classY.get;\n"
          + // valid x10
          "        ClassZ.doSomething();\n"
          + // valid
          "        try {} catch (ExceptionA | ExceptionB b) {}\n"
          + // valid x2
          "    }\n"
          + "    @Annotation2 @Annotation3\n"
          + // valid x2
          "    public void test() throws ExceptionC {\n"
          + // valid
          "        throw new ExceptionD;\n"
          + // valid
          "    }\n"
          + "\n"
          + "}";

  private JavaAnalyzer analyzer;

  @BeforeEach
  public void setUp() {
    this.analyzer = new JavaAnalyzer();
  }

  /**
   * imports: 7 classDefinition: 5 attributes: 5 constructor: 29 test()-method: 4
   *
   * <p>missing: RandomClass3, Annotation2, Annotation3
   */
  @Test
  public void testGetValidImports() {
    List<String> imports = analyzer.getValidImports(fileContent);
    Assertions.assertEquals(50, imports.size());
    Assertions.assertEquals("java.util.Objects", imports.get(0));
    Assertions.assertEquals("java.io.BufferedReader", imports.get(1));
    Assertions.assertEquals("org.junit.jupiter.api.*", imports.get(2));
    Assertions.assertEquals("org.wickedsource.dependencytree.sbdfjksbdf", imports.get(3));
    Assertions.assertEquals("org.wickedsource.dependencytree.fhzgkobd", imports.get(4));
    Assertions.assertEquals(
        "org.wickedsource.dependencytree.example.somepackage.CoreDependencyTest5", imports.get(5));
    Assertions.assertEquals(
        "org.wickedsource.dependencytree.example.wildcardpackage.*", imports.get(6));
    Assertions.assertEquals("Typ1", imports.get(7));
    Assertions.assertEquals("Typ2", imports.get(8));
    Assertions.assertEquals("ClassA", imports.get(9));
    Assertions.assertEquals("InterfaceB", imports.get(10));
    Assertions.assertEquals("interfaceC", imports.get(11));
    Assertions.assertEquals("Annotation1", imports.get(12));
    Assertions.assertEquals("RandomClass3", imports.get(13));
    Assertions.assertEquals("RandomClass2", imports.get(14));
    Assertions.assertEquals("RandomClass", imports.get(15));
    Assertions.assertEquals(
        "org.wickedsource.dependencytree.example.somepackage.FullyClassifiedDependencyTest",
        imports.get(16));
    Assertions.assertEquals("CoreTest", imports.get(17));
    Assertions.assertEquals("ClassB", imports.get(18));
    Assertions.assertEquals("ClassC", imports.get(19));
    Assertions.assertEquals("ClassD", imports.get(20));
    Assertions.assertEquals("ClassE.create", imports.get(21));
    Assertions.assertEquals("ClassF", imports.get(22));
    Assertions.assertEquals("ClassF1.create", imports.get(23));
    Assertions.assertEquals("ClassG.create", imports.get(24));
    Assertions.assertEquals("classH.create", imports.get(25));
    Assertions.assertEquals("ClassI", imports.get(26));
    Assertions.assertEquals("ClassJ", imports.get(27));
    Assertions.assertEquals("ClassK.create", imports.get(28));
    Assertions.assertEquals("classL.create", imports.get(29));
    Assertions.assertEquals("ClassM", imports.get(30));
    Assertions.assertEquals("ClassN", imports.get(31));
    Assertions.assertEquals("ClassO.create", imports.get(32));
    Assertions.assertEquals("ClassP", imports.get(33));
    Assertions.assertEquals("ClassQ.isEmpty", imports.get(34));
    Assertions.assertEquals("classR.isEmpty", imports.get(35));
    Assertions.assertEquals("classS.isEmpty", imports.get(36));
    Assertions.assertEquals("classT.size", imports.get(37));
    Assertions.assertEquals("classU.size", imports.get(38));
    Assertions.assertEquals("classV.size", imports.get(39));
    Assertions.assertEquals("classW.size", imports.get(40));
    Assertions.assertEquals("ClassX.get", imports.get(41));
    Assertions.assertEquals("classY.get", imports.get(42));
    Assertions.assertEquals("ClassZ.doSomething", imports.get(43));
    Assertions.assertEquals("ExceptionA", imports.get(44));
    Assertions.assertEquals("ExceptionB", imports.get(45));
    Assertions.assertEquals("Annotation2", imports.get(46));
    Assertions.assertEquals("Annotation3", imports.get(47));
    Assertions.assertEquals("ExceptionC", imports.get(48));
    Assertions.assertEquals("ExceptionD", imports.get(49));
  }

  @Test
  public void testGetFirstSeparator() {
    String test = "= ((ClassQ.isEmpty() && classR.isEmpty())";
    // should return first '(' after ClassQ.isEmpty, because there is no potential import between
    // begin and all separators before that '('
    Assertions.assertEquals(19, test.indexOf(analyzer.getFirstSeparator(test)));
  }

  @Test
  public void testDependencyStrings() {
    String line =
        "import org.wickedsource.dependencytree.sbdfjksbdf; import org.wickedsource.dependencytree.fhzgkobd;";
    // should return first '(' after ClassQ.isEmpty, because there is no potential import between
    // begin and all separators before that '('
    List<String> imports = analyzer.getDependenciesFromImportLine(line);
    Assertions.assertEquals(2, imports.size());
    Assertions.assertEquals("org.wickedsource.dependencytree.sbdfjksbdf", imports.get(0));
    Assertions.assertEquals("org.wickedsource.dependencytree.fhzgkobd", imports.get(1));
  }

  @Test
  public void testImportDependencyStrings() {
    String line =
        "ClassP b = ((ClassQ.isEmpty() && classR.isEmpty()) || classS.isEmpty || ((classT.size > classU.size) && classV.size < classW.size)) ? ClassX.get : classY.get;";
    // should return first '(' after ClassQ.isEmpty, because there is no potential import between
    // begin and all separators before that '('
    List<String> imports = analyzer.getDependenciesFromLine(line);
    Assertions.assertEquals(10, imports.size());
    Assertions.assertEquals("ClassP", imports.get(0));
    Assertions.assertEquals("ClassQ.isEmpty", imports.get(1));
    Assertions.assertEquals("classR.isEmpty", imports.get(2));
    Assertions.assertEquals("classS.isEmpty", imports.get(3));
    Assertions.assertEquals("classT.size", imports.get(4));
    Assertions.assertEquals("classU.size", imports.get(5));
    Assertions.assertEquals("classV.size", imports.get(6));
    Assertions.assertEquals("classW.size", imports.get(7));
    Assertions.assertEquals("ClassX.get", imports.get(8));
    Assertions.assertEquals("classY.get", imports.get(9));
  }
}
