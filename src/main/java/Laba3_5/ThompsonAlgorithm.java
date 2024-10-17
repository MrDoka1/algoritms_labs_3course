package Laba3_5;

import Laba1.DKA;
import Laba2.NKA;

import java.util.*;

public class ThompsonAlgorithm {
    public static DKA start(NKA nka) {
        final Map<String, Map<String, List<String>>> transitions = nka.getTransitions();
        final List<String> trueStates = nka.getTrueStates();

        final Map<String, Map<String, String>> dkaTransitions = new HashMap<>();
        final List<String> dkaTrueStates = new ArrayList<>();

        Queue<Set<String>> queue = new LinkedList<>();
        Set<String> startState = getNextState("q0", "E", transitions);
        queue.add(startState);
        Set<Set<String>> passedStates = new HashSet<>();

        while (!queue.isEmpty()) {
            Set<String> state = queue.remove();
            if (!passedStates.contains(state)) {
                passedStates.add(state);

                Set<String> nextState0 = new HashSet<>();
                Set<String> nextState1 = new HashSet<>();

                for (String st : state) {
                    nextState0.addAll(getNextState(st, "0", transitions));
                    nextState1.addAll(getNextState(st, "1", transitions));
                }

                if (nextState0.stream().anyMatch(trueStates::contains)) dkaTrueStates.add(setToString(nextState0));
                if (nextState1.stream().anyMatch(trueStates::contains)) dkaTrueStates.add(setToString(nextState1));
                queue.add(nextState0);
                queue.add(nextState1);
                Map<String, String> transition = new HashMap<>();
                transition.put("0", setToString(nextState0));
                transition.put("1", setToString(nextState1));
                dkaTransitions.put(setToString(state), transition);
            }
        }
        return new DKA(dkaTransitions, dkaTrueStates, setToString(startState));
    }

    private static Set<String> getNextState(String st, String value, final Map<String, Map<String, List<String>>> transitions) {
        if (transitions.get(st).get(value) == null) return Collections.emptySet();

        List<String> states = new ArrayList<>(transitions.get(st).get(value));

        Set<String> nextStates = new HashSet<>();
        for (int i = 0; i < states.size(); i++) {
            String state = states.get(i);
            if (transitions.get(state).containsKey("E")) {
                states.addAll(transitions.get(state).get("E"));
            } else {
                nextStates.add(state);
            }
        }
        return nextStates;
    }

    private static String setToString(Set<String> set) {
        return String.join("", set);
    }
}