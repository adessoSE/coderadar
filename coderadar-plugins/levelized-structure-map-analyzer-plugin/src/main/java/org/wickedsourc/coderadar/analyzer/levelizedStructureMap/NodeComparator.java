package org.wickedsourc.coderadar.analyzer.levelizedStructureMap;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
    public int compare(Node o1, Node o2) {
        // if o1 has a dependency on o2 and o2 does not have an dependency on o1
        //   o1 is before o2
        // else if o2 has a dependency on o1 and o1 does not have an dependency on o2
        //   o2 is before o1
        // else if o1 has more dependencies on o2 than o2 on o1
        //   o1 is before o2
        // else if o2 has more dependencies on o1 than o1 on o2
        //   o2 is before o1
        // else if o1 has more dependencies than o2
        //   o1 is before o2
        // else if o2 has more dependencies than o1
        //   o2 is before o1
        // else if o1 is a directory and o2 is not
        //   o1 is before o2
        // else if o2 is a directory and o1 is not
        //   o2 is before o1
        // else compare o1 and o2 lexically
        if (o1.hasDependencyOn(o2) && !o2.hasDependencyOn(o1)) {
            return -1;
        } else if (o2.hasDependencyOn(o1) && !o1.hasDependencyOn(o2)) {
            return 1;
        } else if (o1.countDependenciesOn(o2).size() > o2.countDependenciesOn(o1).size()) {
            return -1;
        } else if (o1.countDependenciesOn(o2).size() < o2.countDependenciesOn(o1).size()) {
            return 1;
        } else if (o1.getDependencies().size() > o2.getDependencies().size()) {
            return -1;
        } else if (o1.getDependencies().size() < o2.getDependencies().size()) {
            return 1;
        } else if (o1.hasChildren() && !o2.hasChildren()) {
            return -1;
        } else if (!o1.hasChildren() && o2.hasChildren()) {
            return 1;
        } else {
            return o1.getFilename().compareTo(o2.getFilename());
        }
    }
}
