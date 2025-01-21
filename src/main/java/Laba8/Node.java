package Laba8;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Node implements Cloneable {
    String data;
    List<Node> nodes;

    public Node() {
    }

    public Node(String data) {
        this.data = data;
    }

    @Override
    protected Node clone() throws CloneNotSupportedException {
        Node clonedNode = (Node) super.clone();
        if (this.nodes != null) {
            clonedNode.nodes = new ArrayList<>();
            for (Node child : this.nodes) {
                clonedNode.nodes.add(child.clone());
            }
        }
        return clonedNode;
    }

    /*protected Node clone() {
        Node node = new Node(data);
        if (nodes != null) node.nodes = new ArrayList<>(nodes);
        return node;
    }*/
}