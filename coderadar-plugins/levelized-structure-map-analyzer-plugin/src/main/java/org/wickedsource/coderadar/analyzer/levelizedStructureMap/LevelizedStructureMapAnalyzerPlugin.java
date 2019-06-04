package org.wickedsource.coderadar.analyzer.levelizedStructureMap;

import org.wickedsource.coderadar.analyzer.api.AnalyzerException;
import org.wickedsource.coderadar.analyzer.api.EntityMetric;
import org.wickedsource.coderadar.analyzer.api.ProjectAnalyzerPlugin;

import java.io.File;
import java.util.LinkedList;

public class LevelizedStructureMapAnalyzerPlugin implements ProjectAnalyzerPlugin<Node> {

    @Override
    public EntityMetric<Node> analyzeProject(String projectPath, String basePackage) throws AnalyzerException {
        File rootFile = new File(projectPath + basePackage);
        String basePackageDot = basePackage.replace("/", ".");

        Node node = new Node(new LinkedList<>(), rootFile.getPath(), rootFile.getName(), basePackageDot);
        DependencyTree dependencyTree = new DependencyTree(basePackage, basePackageDot, node);
        dependencyTree.createTree(node);
        dependencyTree.setDependencies(node);
        node.setDependencies(new LinkedList<>());
        dependencyTree.sortTree(node);
        dependencyTree.setLayer(node);
        return new EntityMetric<Node>("levelizedStructureMap", node);
    }
}
