package Laba3;

import Laba1.DKA;

import java.util.*;

public class MinimizationDKA {
    private final CustomMatrix customMatrix;
    private final Map<String, Map<String, String>> transitions;
    private final List<String> states;
    private final List<String> trueStates;
    private final String startState;

    public MinimizationDKA(DKA dka) {
        customMatrix = new CustomMatrix(dka);
        transitions = new HashMap<>(dka.getTransitions());
        states = new ArrayList<>(dka.getTransitions().keySet());
        trueStates = new ArrayList<>(dka.getTrueStates());
        startState = dka.getStartState();
    }

    public DKA start() {
        /*Set<String> comingSates = new HashSet<>();
        comingSates.add(startState);
        transitions.forEach((k, v) -> {
            comingSates.add(v.get("0"));
            comingSates.add(v.get("1"));
        });*/

        states.forEach(state -> {
//            Диагональ
            customMatrix.setValue(state, state);

//            Отличие от допускающих состояний
            trueStates.forEach(trueState -> {
                if (!trueStates.contains(state)) {
                    customMatrix.setValue(state, trueState);
                }
            });
        });

        boolean changed = true;
        boolean emptiness;

        while (changed) {
            changed = false;
            emptiness = false;

            for (int i = 0; i < states.size() - 1; i++) {
                for (int j = 1; j < states.size(); j++) {
                    if (i != j) {
                        String state1 = states.get(i);
                        String state2 = states.get(j);

                        if (!customMatrix.getValue(state1, state2)) {
                            if (areDistinguishable(state1, state2, "0")) {
                                customMatrix.setValue(state1, state2);
                                changed = true;
                            } else if (areDistinguishable(state1, state2, "1")) {
                                customMatrix.setValue(state1, state2);
                                changed = true;
                            } else {
                                emptiness = true;
                            }
                        }
                    }
                }
            }

        }

        /* --- Сбор новых состояний --- */
        Set<Set<String>> newStates = new HashSet<>();
        states.forEach(state -> {
            Set<String> newState = new HashSet<>(List.of(state));
            boolean[] column = customMatrix.getColumn(state);
            // Добавление в одно состояние, если неразличимы
            for (int i = 0; i < column.length; i++) {
                if (!column[i]) newState.add(states.get(i));
            }
            newStates.add(newState);
        });
        /*--- Сбор новых состояний end ---*/

        /* --- Сбор новых переходов --- */
        Map<String, Map<String, String>> newTransitions = new HashMap<>();

        newStates.forEach(newState -> {
            Map<String, String> newTransition = new HashMap<>();
            String currentState = setToString(newState);
            String firstState = newState.stream().findFirst().get();

            Set<String> states0 = newStates.stream()
                    .filter(st -> st.contains(transitions.get(firstState).get("0")))
                    .findFirst().get();
            Set<String> states1 = newStates.stream()
                    .filter(st -> st.contains(transitions.get(firstState).get("1")))
                    .findFirst().get();

            newTransition.put("0", setToString(states0));
            newTransition.put("1", setToString(states1));
            newTransitions.put(currentState, newTransition);
        });
        /*--- Сбор новых переходов end ---*/

        /* --- Сбор новых конечных состояний --- */
        Set<String> newTrueStates = new HashSet<>();
        newStates.forEach(newState -> {
            if (newState.stream().anyMatch(trueStates::contains)) newTrueStates.add(setToString(newState));
        });
        /* --- Сбор новых конечных состояний end --- */
        String newStartState = setToString(newStates.stream()
                .filter(state ->
                        state.stream().anyMatch(startState::equals))
                .findFirst().get());

        return new DKA(newTransitions, new ArrayList<>(newTrueStates), newStartState);
    }

    public boolean areDistinguishable(String state1, String state2, String value) {
        String state1NextValue = transitions.get(state1).get(value);
        String state2NextValue = transitions.get(state2).get(value);
        if (state1NextValue.equals(state2NextValue)) return false;
        return customMatrix.getValue(state1NextValue, state2NextValue);
    }

    private static String setToString(Set<String> set) {
        return String.join("", set);
    }
}