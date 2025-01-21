package Laba5;

import java.util.*;

public class GrammarAnalyzer {
    private final HashMap<String, List<String>> grammarHashMap;

    public GrammarAnalyzer(HashMap<String, List<String>> grammarHashMap) {
        this.grammarHashMap = grammarHashMap;
    }

    public boolean analyze(String string) {
        List<String> stringList = new ArrayList<>(List.of(string.split("")));
        Queue<QueueElement> queue = new LinkedList<>();
        queue.add(new QueueElement(stringList, new ArrayList<>(List.of("S"))));
        while (!queue.isEmpty()) {
            QueueElement queueElement = queue.remove();
            List<String> strings = queueElement.strings;
            List<String> elements = queueElement.elements;

//            System.out.println("s: " + strings);
//            System.out.println("e: " + elements);

            if (elements.isEmpty()) {
                if (strings.isEmpty()) return true;
            } else {
                if (grammarHashMap.containsKey(elements.get(0))) {
                    List<String> removeElements = new ArrayList<>(elements);
                    removeElements.remove(0);
                    grammarHashMap.get(elements.get(0)).forEach(construction -> {
                        List<String> newElements = new ArrayList<>(List.of(construction.split(" ")));
                        newElements.addAll(removeElements);
                        //queueElement.elements = newElements;
                        queue.add(new QueueElement(new ArrayList<>(strings), newElements));
                    });
                } else {
                    if (!strings.isEmpty()) {
                        if (strings.get(0).equals(elements.get(0))) {
                            queueElement.strings.remove(0);
                            queueElement.elements.remove(0);
                            queue.add(queueElement);
                        } else if (elements.get(0).isEmpty()) {
                            queueElement.elements.remove(0);
                            queue.add(queueElement);
                        }
                    }
                }
            }
        }
        return false;
    }
}

class QueueElement {
    public List<String> strings;
    public List<String> elements;

    public QueueElement(List<String> strings, List<String> elements) {
        this.strings = strings;
        this.elements = elements;
    }
}