package Laba8;

import classes.ReaderCSV;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class Laba8 {
    public static void main(String[] args) throws CloneNotSupportedException {
        GrammarAnalyzer grammarAnalyzer = new GrammarAnalyzer(ReaderCSV.readGrammar("Laba8/grammar"));
        List<String> stringList = List.of(
                "",

                "<a>" +
                        "<b>" +
                        "<a>dsfsdfsdf</a>" +
                        "<a></a>" +
                        "</b>" +
                        "</a>"

                //"<a></b>",

                //"<a>"

        );
        stringList.forEach(string -> System.out.println(grammarAnalyzer.analyze(string).result()));
        AnalyzeResult analyzeResult = grammarAnalyzer.analyze(stringList.get(1));
        writeToCSV(analyzeResult.tree(), 1);

        analyzeResult.tree().generateJsonFile("./src/main/java/Laba8/tree.json");
        TreeVisualizer treeVisualizer = new TreeVisualizer(analyzeResult.tree().getRoot());
    }

    private static void writeToCSV(Tree tree, int index) {
        String PATH = "./src/main/java/Laba8/";
        StringBuilder stringBuilder = new StringBuilder();
        Queue<Node> queue = new LinkedList<>();
        queue.add(tree.getRoot());
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

        try {
            Files.write(Paths.get(PATH + "tree" + index + ".csv"), Collections.singletonList(stringBuilder));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}