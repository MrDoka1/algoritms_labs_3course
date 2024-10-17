package Laba1;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class DKA {
    private final Map<String, Map<String, String>> transitions;
    private final List<String> trueStates;
    private final String startState;

    public DKA(Map<String, Map<String, String>> transitions, List<String> trueStates, String startState) {
        this.transitions = transitions;
        this.trueStates = trueStates;
        this.startState = startState;
    }

    public boolean solve(String input) {
        String[] values = input.split("");
        String currentState = startState;
        for (String value : values) {
            currentState = transitions.get(currentState).get(value);
        }
        return trueStates.contains(currentState);
    }
}