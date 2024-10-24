package Laba4;

import Laba2.NKA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParserRegExp {
    public static NKA start(String regExp) {
//        try {
            List<String> expressions = new ArrayList<>();

            int counter = 0;
            List<String> regExpList = new ArrayList<>(List.of(regExp.split("")));
            for (int i = 0; i < regExpList.size(); i++) {
                String c = regExpList.get(i);
                if (c.equals("(") && (regExpList.get(i + 1).equals("0") || regExpList.get(i + 1).equals("1"))) {
                    int start = i;
                    i++;
                    StringBuilder stringBuilder = new StringBuilder();
                    while (!regExpList.get(i).equals(")")) {
                        stringBuilder.append(regExpList.get(i++));
                    }
                    IntStream.range(start, start+stringBuilder.length()+2).forEach(s->regExpList.remove(start));
                    regExpList.add(start, String.valueOf(counter++));
                    expressions.add(stringBuilder.toString());
                    i -= stringBuilder.length() + 1;
                }
            }

            AtomicInteger state = new AtomicInteger(0);
            List<NKA> expressionNKAs = expressions.stream().map(expression -> {
                NKA nka = createNKA(expression, state.get());
                state.set(state.get() + expression.length() + 1);
                return nka;
            }).collect(Collectors.toList());

            System.out.println(regExpList);

//            return parse(regExpList, expressionNKAs, state);
            NKA nka = parse(regExpList, expressionNKAs, state);
            return postProcessingNKA(nka);
//        } catch (NumberFormatException | IndexOutOfBoundsException ignore) {
//            throw new RuntimeException("Неверный синтаксис RegExp");
//        }
    }

    private static NKA parse(List<String> regExpList, List<NKA> expressionNKAs, AtomicInteger state)
            throws NumberFormatException, IndexOutOfBoundsException{
        int i = regExpList.size() - 1;
        String curStr = regExpList.get(i);

        // Условие выхода
        if (regExpList.size() == 1) {
            NKA nka = expressionNKAs.get(Integer.parseInt(regExpList.get(0)));
            expressionNKAs.set(Integer.parseInt(curStr), null);
            return nka;
        }

        if (curStr.equals("*")) {
            if (regExpList.get(i - 1).equals(")")) {
                int start = i - 2;
                int count = 1;
                while (true) {
                    if (regExpList.get(start).equals(")")) {
                        count++;
                    } else if (regExpList.get(start).equals("(")) {
                        count --;
                        if (count == 0) break;
                    }
                    start--;
                }
                NKA nka = multiplication(parse(regExpList.subList(start+1, i-1), expressionNKAs, state));
                regExpList.remove(i); // Удаляем "*"
                regExpList.remove(i - 1); // Удаляем ")"
                regExpList.remove(start); // Удаляем "("
                expressionNKAs.set(Integer.parseInt(regExpList.get(start)), nka); // Вставляем новый NKA
                return concatWithPreParse(start, regExpList, expressionNKAs, state, nka);
            }
            int indexNKA = Integer.parseInt(regExpList.get(i - 1));
            NKA nka = multiplication(expressionNKAs.get(indexNKA));
            regExpList.remove(i); // Удаляем "*"
            expressionNKAs.set(indexNKA, nka); // Вставляем новый NKA
            return concatWithPreParse(i-1, regExpList, expressionNKAs, state, nka);
        }

        if (isNumber(curStr)) {
            if (regExpList.get(i-1).equals("+")) {
                NKA nka = plus(state, parse(regExpList.subList(0, i-1), expressionNKAs, state),
                        expressionNKAs.get(Integer.parseInt(curStr)));
                expressionNKAs.set(Integer.parseInt(curStr), nka); // tyt
                return nka;
            }
            NKA nka = concat(parse(regExpList.subList(0, i), expressionNKAs, state), expressionNKAs.get(Integer.parseInt(curStr)));
            expressionNKAs.set(Integer.parseInt(curStr), nka); // tyt
            return nka;
        }

        String oneSate = String.valueOf(state.getAndIncrement());
        return new NKA(Map.of(oneSate, Map.of("0", List.of(oneSate), "1", List.of(oneSate))),
                List.of(oneSate), oneSate);
    }

    private static NKA concatWithPreParse(int end, List<String> regExpList, List<NKA> expressionNKAs, AtomicInteger state,
                                          NKA nka) {
        if (end == 0) return nka;
        return concat(parse(regExpList.subList(0, end), expressionNKAs, state), nka);
    }

    private static boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }

    private static NKA multiplication(NKA nka) {
        final Map<String, Map<String, List<String>>> transitions = new HashMap<>(nka.getTransitions());
        nka.getTrueStates().forEach(trueState -> {
            transitions.put(trueState, Map.of("E", List.of(nka.getStartState())));
        });
        return new NKA(transitions, List.of(nka.getStartState()), nka.getStartState());
    }

    private static NKA plus(AtomicInteger start, NKA nka1, NKA nka2) {
        final String startSate = String.valueOf(start.getAndIncrement());
        final Map<String, Map<String, List<String>>> transitions = new HashMap<>(nka1.getTransitions());
        transitions.putAll(nka2.getTransitions());
        transitions.put(startSate, Map.of("E", List.of(nka1.getStartState(), nka2.getStartState())));
        final List<String> trueStates = new ArrayList<>(nka1.getTrueStates());
        trueStates.addAll(nka2.getTrueStates());
        return new NKA(transitions, trueStates, startSate);
    }

    private static NKA concat(NKA nka1, NKA nka2) {
        final Map<String, Map<String, List<String>>> transitions = new HashMap<>(nka1.getTransitions());
        transitions.putAll(nka2.getTransitions());
        nka1.getTrueStates().forEach(trueState -> {
            transitions.put(trueState, Map.of("E", List.of(nka2.getStartState())));
        });
        return new NKA(transitions, nka2.getTrueStates(), nka1.getStartState());
    }

    private static NKA createNKA(String expression, int start) {
        int current = start;
        final Map<String, Map<String, List<String>>> transitions = new HashMap<>();
        for (String str : expression.split("")) {
            transitions.put(String.valueOf(current), Map.of(str, List.of(String.valueOf(++current))));
        }
        transitions.put(String.valueOf(current), new HashMap<>());
        return new NKA(transitions, List.of(String.valueOf(current)), String.valueOf(start));
    }

    private static NKA postProcessingNKA(NKA nka) {
        List<String> newTrueStates = new ArrayList<>(nka.getTrueStates());
        boolean change = false;
        for (int i = 0; i < newTrueStates.size(); i++) {
            if (nka.getTransitions().get(newTrueStates.get(i)).containsKey("E")) {
                newTrueStates.addAll(nka.getTransitions().get(newTrueStates.get(i)).get("E"));
                change = true;
            }
        }
        if (!change) return nka;
        return new NKA(nka.getTransitions(), newTrueStates, nka.getStartState());
    }
}