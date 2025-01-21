package Laba8;

import java.util.*;

public class GrammarAnalyzer {
    private final HashMap<String, List<String>> grammarHashMap;

    public GrammarAnalyzer(HashMap<String, List<String>> grammarHashMap) {
        this.grammarHashMap = grammarHashMap;
    }

    public AnalyzeResult analyze(String string) {
        List<String> stringList = new ArrayList<>(List.of(string.split("")));
        Queue<QueueElement> queue = new LinkedList<>();
        Node startNode = new Node("S");
        List<Node> nodeList = new ArrayList<>(List.of(startNode));
        Tree tree = new Tree(startNode, grammarHashMap.keySet());
        queue.add(new QueueElement(stringList, new ArrayList<>(List.of("S")), nodeList, tree));

        while (!queue.isEmpty()) {
            QueueElement queueElement = queue.remove();
            List<String> strings = queueElement.strings;
            List<String> elements = queueElement.elements;
            List<Node> nodes = queueElement.nodes;
            Tree currentTree = queueElement.tree;
            //TreeVisualizer treeVisualizer = new TreeVisualizer(currentTree.getRoot());


//            System.out.println("s: " + strings);
//            System.out.println("e: " + elements);
//            System.out.println("n: " + nodes.stream().map(node -> node.data).toList());
            //System.out.print("t: ");
            //currentTree.print();

            if (elements.isEmpty()) {
                if (strings.isEmpty()) return new AnalyzeResult(true, currentTree);
            } else {
                if (grammarHashMap.containsKey(elements.get(0))) {
                    List<String> removeElements = new ArrayList<>(elements);
                    removeElements.remove(0);
                    //Node currentNode = nodes.remove(0);

                    grammarHashMap.get(elements.get(0)).forEach(construction -> {
                        List<String> newElements = new ArrayList<>(List.of(construction.split(" ")));
                        List<Node> newNodes = new ArrayList<>(newElements.stream().map(el -> new Node(el)).toList());
                        newElements.addAll(removeElements);
                        //currentNode.nodes = newNodes;
                        List<Node> newNodes2 = new ArrayList<>(newNodes);
                        //newNodes2.addAll(nodes);


                        Tree newTree = null;
                        try {
                            newTree = currentTree.clone();
                        } catch (CloneNotSupportedException e) {
                            throw new RuntimeException(e);
                        }
                        //System.out.println(elements.get(0) + " " + newTree.getLeftNode().data);
                        //newTree.getLeftNode().nodes = (newElements.stream().map(el -> new Node(el)).toList());
                        //System.out.println(newElements.stream().map(el -> new Node(el)).toList());
                        newTree.addNodes(newNodes);

                        queue.add(new QueueElement(new ArrayList<>(strings), newElements, newNodes2, newTree));
                    });
                } else {
                    if (!strings.isEmpty()) {
                        if (strings.get(0).equals(elements.get(0))) {
                            queueElement.strings.remove(0);
                            queueElement.elements.remove(0);
                            queueElement.nodes.remove(0);
                            queue.add(queueElement);
                        } else if (elements.get(0).isEmpty()) {
                            queueElement.elements.remove(0);
                            queueElement.nodes.remove(0);
                            queue.add(queueElement);
                        }
                    }
                }
            }
        }
        return new AnalyzeResult(false, null);
    }
}

class QueueElement {
    public List<String> strings;
    public List<String> elements;
    List<Node> nodes;
    public Tree tree;

    public QueueElement(List<String> strings, List<String> elements, List<Node> nodes, Tree tree) {
        this.strings = strings;
        this.elements = elements;
        this.nodes = nodes;
        this.tree = tree;
    }
}