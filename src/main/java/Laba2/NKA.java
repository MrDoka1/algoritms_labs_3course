package Laba2;

import lombok.Getter;

import java.util.*;

@Getter
public class NKA {
    private final Map<String, Map<String, List<String>>> transitions;
    private final List<String> trueStates;
    private final String startState;

    public NKA(Map<String, Map<String, List<String>>> transitions, List<String> trueStates, String startState) {
        this.transitions = transitions;
        this.trueStates = trueStates;
        this.startState = startState;
    }

    public boolean solve(String input) {
        Queue<List<String>> queue = new LinkedList<>();  // q0, "10010110"
        queue.add(List.of(startState, input));

        while (!queue.isEmpty()) {
            List<String> state = queue.remove();
            String currentState = state.get(0);
            String string = state.get(1);

            Map<String, List<String>> possibleStates = transitions.get(currentState);

            if (string.isEmpty()) {
                if (!possibleStates.containsKey("E")) {
                    if (queue.isEmpty()) return trueStates.contains(currentState);
                }
            }

            if (possibleStates.containsKey("E")) {
                List<String> newStates = possibleStates.get("E");
                newStates.forEach(newState -> queue.add(List.of(newState, string)));
            } else if (!string.isEmpty()) {
                String value = String.valueOf(string.charAt(0));
                String newString = string.substring(1);
                List<String> newStates = possibleStates.get(value);
                if (newStates != null) {
                    newStates.forEach(newState -> queue.add(List.of(newState, newString)));
                }
            }
        }
        return false;
    }
}