package org.wickedsource.coderadar.analyzer.levelizedStructureMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
public class DependencyTreeService {

    public String getDependencyTree(File rootFile, String basepackage) {
        if (rootFile.isDirectory()) {
            Node baseRoot = new Node(new LinkedList<>(), rootFile.getPath(), rootFile.getName(), "");
            DependencyTree dependencyTree = new DependencyTree(basepackage, basepackage.replace("/", "."), baseRoot);
            dependencyTree.createTree(baseRoot);
            dependencyTree.setDependencies(baseRoot);
            baseRoot.setDependencies(new LinkedList<>());
            dependencyTree.sortTree(baseRoot);
            dependencyTree.setLayer(baseRoot);

            ObjectMapper objectMapper = new ObjectMapper();
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(Node.class, new NodeSerializer());
            objectMapper.registerModule(simpleModule);
            try {
                String serialized = objectMapper.writeValueAsString(baseRoot);
                return serialized;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getDependencyTree(ArrayList<File> files, File rootFile, String basepackage) {






        if (rootFile.isDirectory()) {
            Node baseRoot = new Node(new LinkedList<>(), rootFile.getPath(), rootFile.getName(), "");
            DependencyTree dependencyTree = new DependencyTree(basepackage, basepackage.replace("/", "."), baseRoot);
            dependencyTree.createTree(baseRoot);
            dependencyTree.setDependencies(baseRoot);
            baseRoot.setDependencies(new LinkedList<>());
            dependencyTree.sortTree(baseRoot);
            dependencyTree.setLayer(baseRoot);

            ObjectMapper objectMapper = new ObjectMapper();
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(Node.class, new NodeSerializer());
            objectMapper.registerModule(simpleModule);
            try {
                String serialized = objectMapper.writeValueAsString(baseRoot);
                return serialized;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Get a list of all paths where 'src/main/java/BASEPACKAGE' follows.
     *
     * @param base projectDir acting as base
     * @return List of paths to the modules
     */
    private List<String> getModulePaths(File base) {
        List<String> modulePaths = new LinkedList<>();
        if (base.exists() && base.isDirectory()) {
            // for all children
            for (File f : Objects.requireNonNull(base.listFiles())) {
                // check if filePath contains src/main/java
                if (f.getPath().contains("/src/main/java") || f.getPath().contains("\\src\\main\\java")) {
                    // module found
                    // get name and path of module and add it to list
                    modulePaths.add(f.getParentFile().getParentFile().getParentFile().getPath());
                } else {
                    modulePaths.addAll(getModulePaths(f));
                }
            }
        }
        return modulePaths;
    }
}
