package Laba8;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Getter
public class Tree implements Cloneable {
    Node root;
    private Set<String> noTerminals;

    public Tree() {}

    public Tree(Node root, Set<String> noTerminals) {
        this.root = root;
        this.noTerminals = noTerminals;
    }

    public void addNodes(List<Node> nodeList) {
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node currentNode = stack.pop();
            if (currentNode.nodes == null) {
                if (noTerminals.contains(currentNode.data)) {
                    StringBuilder nodes = new StringBuilder();
                    nodeList.forEach(node1 -> nodes.append(node1.data).append(" "));
//                    System.out.println(currentNode.data + ", " + nodes);
                    currentNode.nodes = new ArrayList<>(nodeList);
                    return;
                }
            } else {
                List<Node> nodes = new ArrayList<>(currentNode.nodes);
                Collections.reverse(nodes);
                stack.addAll(nodes);
            }

        }
    }

    public Node getLeftNode() {
        return getLeftNode(root);
    }
    private Node getLeftNode(Node node) {
        while (true) {
            if (node.nodes == null && noTerminals.contains(node.data)) return node;
            if (node.nodes == null) return null;
            for (Node node1 : node.nodes) {
                if (noTerminals.contains(node1.data)) {
                    Node leftNode = getLeftNode(node1);
                    if (leftNode != null) return leftNode;
                }
            }
        }
    }

    @Override
    protected Tree clone() throws CloneNotSupportedException {
        Tree clonedTree = (Tree) super.clone();
        clonedTree.root = this.root.clone();
        return clonedTree;
    }

    public void print() {
        StringBuilder stringBuilder = new StringBuilder();
        Queue<Node> queue = new LinkedList<>();
        queue.add(this.getRoot());
        while (!queue.isEmpty()) {
            Node node = queue.remove();
            if (node.nodes == null) {
                stringBuilder.append(node.data).append("\n");
            } else {
                StringBuilder nodes = new StringBuilder();
                node.nodes.forEach(node1 -> nodes.append(node1.data).append(" "));
                stringBuilder.append(node.data).append(",").append(nodes).append("\n");
                queue.addAll(node.nodes);
            }
        }
//        System.out.println(stringBuilder);
    }

    public void generateJsonFile(String fileName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(fileName), root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}