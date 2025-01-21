package Laba7;

import Laba6.DataTransaction;
import Laba6.PushDownAutomaton;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GrammarToPushDownAutomaton {
    // currentState, inputSymbol, stackSymbol, DataTransaction(backStackList, nextState)
    private final Map<String, Map<String, Map<String, List<DataTransaction>>>> transactions = new HashMap<>();
    String startState = "q0";

    private final HashMap<String, List<String>> grammarHashMap;

    public GrammarToPushDownAutomaton(HashMap<String, List<String>> grammarHashMap) {
        this.grammarHashMap = grammarHashMap;
    }

    public PushDownAutomaton getPushDownAutomaton() {
        // q0 -- E --> q1 / Z0 -> S,Z0
        addRuleTransaction(startState, "E", "Z0", new ArrayList<>(List.of("Z0", "S")),
                new ArrayList<>(List.of("q1")));

        grammarHashMap.forEach((terminal, listRules) -> {
            if (listRules.size() == 1 && (listRules.get(0).split(" ").length == 1)) {
                String symbol = listRules.get(0);
                addRuleTransaction("q1", symbol, terminal, new ArrayList<>(),
                        new ArrayList<>(List.of("q1")));
            } else {
                listRules.forEach(rule -> {
                    List<String> terminals = new ArrayList<>(List.of(rule.split(" ")));
                    Collections.reverse(terminals);
                    if (rule.isEmpty()) {
                        addRuleTransaction("q1", "E", terminal, new ArrayList<>(),
                                new ArrayList<>(List.of("q1")));
                    } else {
                        addRuleTransaction("q1", "E", terminal, terminals,
                                new ArrayList<>(List.of("q1")));
                    }
                });
            }
        });

        addRuleTransaction("q1", "E", "Z0", new ArrayList<>(), new ArrayList<>(List.of("q1")));

        return new PushDownAutomaton(transactions, startState);
    }

    private void addRuleTransaction(String currentState, String inputSymbol, String stackSymbol,
                                    List<String> backStackList, List<String> nextState) {
        transactions.computeIfAbsent(currentState, k -> new HashMap<>()).computeIfAbsent(inputSymbol, k -> new HashMap<>())
                .computeIfAbsent(stackSymbol, k -> new ArrayList<>()).add(new DataTransaction(backStackList, nextState));
    }
}