package classes;

import Laba2.NKA;

import java.util.List;
import java.util.Map;

public class PrinterKA {
    public static void print(NKA nka) {
        Map<String, Map<String, List<String>>> map = nka.getTransitions();
        System.out.println(nka.getStartState() + " " + nka.getTrueStates());
        for (String outerKey : map.keySet()) {
            System.out.println(outerKey);
            Map<String, List<String>> innerMap = map.get(outerKey);

            for (String innerKey : innerMap.keySet()) {
                List<String> list = innerMap.get(innerKey);

                System.out.println("    " + innerKey + " -> " + list);
            }

        }
    }
}