package io.reflectoring.coderadar.dependencymap.domain;

import io.reflectoring.coderadar.plugin.api.ChangeType;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.Data;

@Data
public class CompareNode {

  private String filename;
  private String path;
  private String packageName;
  private int level;
  private List<CompareNode> children;
  private List<CompareNodeDTO> dependencies;
  private ChangeType changed;

  public CompareNode(String filename, String path, String packageName, ChangeType changed) {
    this.filename = filename;
    this.path = path;
    this.packageName = packageName;
    children = new CopyOnWriteArrayList<>();
    dependencies = new CopyOnWriteArrayList<>();
    this.changed = changed;
  }

  public CompareNode() {}

  /**
   * Check if this Node-object has children.
   *
   * @return true if this Node-object has children.
   */
  public boolean hasChildren() {
    return !children.isEmpty();
  }

  /**
   * Add a Node-object to this Node-object's children.
   *
   * @param node Node-object to add as a child.
   */
  public void addToChildren(CompareNode node) {
    children.add(node);
  }

  /**
   * Add a Node-object to this Node-object's dependencies.
   *
   * @param node path of Node-object to add as a dependency.
   */
  public void addToDependencies(CompareNode node) {
    if (!filename.equals(node.getFilename())) {
      // create a CompareNodeDTO representing the CompareNode and set its changeType to UNCHANGED
      // because there is nothing to compare
      CompareNodeDTO dto = new CompareNodeDTO(node.getPath(), ChangeType.UNCHANGED);
      if (!dependencies.contains(dto)) {
        dependencies.add(dto);
      } else {
        for (CompareNodeDTO dependency : dependencies) {
          if (dto.equals(dependency) && !dependency.getChanged().equals(dto.getChanged())) {
            dependencies.add(dto);
            break;
          }
        }
      }
    }
  }

  /**
   * Add Node-objects to this Node-object's dependencies.
   *
   * @param nodes List of paths of Node-objects to add as dependencies.
   */
  public void addToDependencies(List<CompareNodeDTO> nodes) {
    dependencies.addAll(nodes);
  }

  /**
   * Finds a child Node-object with a given filename.
   *
   * @param name filename to use.
   * @return found Node-object. Returns null if no Node-object found.
   */
  public CompareNode getChildByName(String name) {
    return hasChildren()
        ? children.stream()
            .filter(child -> child.getFilename().equals(name))
            .findFirst()
            .orElse(null)
        : null;
  }

  /**
   * Searches a Node-object of a specified path.
   *
   * @param nodePath path of the Node-object.
   * @return the found Node-object or null if object not found or nodePath invalid.
   */
  public CompareNode getNodeByPath(String nodePath) {
    if (nodePath == null || nodePath.equals("")) {
      return null;
    }
    String[] path = nodePath.split("/");
    CompareNode tmp = this;
    // iterate over every part of the new path
    for (String s : path) {
      // if the Node-object already exists, iterate over it
      if (tmp.getChildByName(s) != null) {
        tmp = tmp.getChildByName(s);
      } else {
        return null;
      }
    }
    return tmp;
  }

  /**
   * Creates a Node-object of a specified path. Non-existent Node-objects on the path will also be
   * created.
   *
   * @param nodePath path of the Node-object.
   * @return the created Node-object or null if object not found or nodePath invalid.
   */
  public CompareNode createNodeByPath(String nodePath, ChangeType changeType) {
    if (nodePath == null || nodePath.equals("")) {
      return null;
    }
    String[] path = nodePath.split("/");
    CompareNode tmp = this;
    // iterate over every part of the new path
    int i = 0;
    while (i < path.length) {
      String s = path[i];
      // if the Node-object already exists, iterate over it
      if (tmp.getChildByName(s) != null) {
        tmp = tmp.getChildByName(s);
      } else {
        // create new Node
        CompareNode node =
            new CompareNode(
                s, tmp.getPath().equals("") ? s : tmp.getPath() + "/" + s, "", changeType);
        tmp.addToChildren(node);
        tmp = node;
      }
      i++;
    }
    return tmp;
  }

  /**
   * Counts dependencies @this has on a given Node-object.
   *
   * @param node Node-object to check.
   * @return amount of dependencies @this has on a given Node-object.
   */
  public int countDependenciesOn(CompareNode node) {
    int counter = 0;
    for (CompareNodeDTO dependency : dependencies) {
      // if @node represents a file
      // else if @node represents a folder
      if (!node.hasChildren()) {
        // if the if @node is found, raise the counter
        if (dependency.getPath().equals(node.getPath())) {
          counter++;
        }
      } else {
        if (dependency.getPath().contains(node.getPath())) {
          String secondPart = dependency.getPath().substring(node.getPath().length() + 1);
          if (node.getNodeByPath(secondPart) != null) {
            counter++;
          }
        }
      }
    }
    return counter;
  }

  /**
   * Check if this has a dependency on a given Node-object.
   *
   * @param node Node-object to check.
   * @return true if this has a dependency on the given Node-object.
   */
  public boolean hasDependencyOn(CompareNode node) {
    for (CompareNodeDTO dependency : dependencies) {
      if (!this.hasChildren() && !node.hasChildren() || this.hasChildren() && !node.hasChildren()) {
        if (dependency.getPath().equals(node.getPath())) {
          return true;
        }
      } else if (!this.hasChildren() && node.hasChildren()
          || this.hasChildren() && node.hasChildren()) {
        if (dependency
            .getPath()
            .contains(
                (node.getPackageName().equals("") ? node.getFilename() : node.getPackageName())
                    .replaceAll("\\.", "/"))) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Get this Node-object's parent Node-object.
   *
   * @param root root Node-object to begin the search by path.
   * @return null if the root Node-object equals this Node-object else the parent Node-object.
   */
  public CompareNode getParent(CompareNode root) {
    if (this.path.equals(root.path)) {
      return null;
    } else if (!path.contains("/")) {
      return root;
    } else {
      String parentPath = this.path.substring(0, this.path.lastIndexOf("/"));
      return root.getNodeByPath(parentPath);
    }
  }

  /**
   * Get a dependency by a given path.
   *
   * @param path path to look for.
   * @return CompareNodeDTO with the given path or else null.
   */
  public CompareNodeDTO getDependencyByPath(String path) {
    if (path == null || dependencies == null) {
      return null;
    }
    return dependencies.stream()
        .filter(dependency -> path.equals(dependency.getPath()))
        .findFirst()
        .orElse(null);
  }

  /**
   * Traverse the Tree in post order. Call method in TraverseInterface for every found Node-object.
   *
   * @param traverseInterface method to call.
   */
  public void traversePost(CompareTreeTraverseInterface traverseInterface) {
    Stack<CompareNode> stack = new Stack<>();
    HashSet<CompareNode> hash = new HashSet<>();
    CompareNode root = this;
    stack.push(root);
    while (!stack.isEmpty()) {
      root = stack.peek();
      if (root.children.isEmpty() || hash.contains(root)) {
        traverseInterface.traverseMethod(stack.pop());
      } else {
        root.children.forEach(stack::push);
        hash.add(root);
      }
    }
  }

  /**
   * Traverse the Tree in pre order. Call method in TraverseInterface for every found Node-object.
   *
   * @param traverseInterface method to call.
   */
  public void traversePre(CompareTreeTraverseInterface traverseInterface) {
    CompareNode root = this;
    Stack<CompareNode> stack = new Stack<>();
    stack.add(root);

    while (!stack.isEmpty()) {
      root = stack.pop();
      stack.addAll(root.children);
      traverseInterface.traverseMethod(root);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof CompareNode) {
      return ((CompareNode) obj).getPath().equals(this.path);
    }
    return false;
  }

  @Override
  public String toString() {
    return "{" + "path='" + path + '\'' + ", changed=" + changed + '}';
  }
}
