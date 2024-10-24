package Laba9;

import classes.TXTService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class TuringMachine {
    private final Map<String, Map<String, List<String>>> table;

    public TuringMachine(Map<String, Map<String, List<String>>> table) {
        this.table = table;
    }

    public String solveAndCreateFile(String string, String filename) {
        String output = solve(string);
        TXTService.writeString(output, filename);
        return output;
    }

    public String solve(String string) {
        final String startState = "q1";
        final String endState = "q0";
        final List<String> values = new ArrayList<>(List.of(string.split("")));
        AtomicInteger pointer = new AtomicInteger();
        int indexStart = values.indexOf("[");
        if (indexStart != -1) {
            pointer.set(indexStart);
            values.remove(indexStart); // remove "["
            values.remove(indexStart+1); // remove "]"
        }

        AtomicInteger shift = new AtomicInteger(0);
        final int startLength = values.size();

        AtomicReference<String> state = new AtomicReference<>(startState);
        while (!state.get().equals(endState)) {
            String currentStr = values.get(pointer.get());
            if (currentStr.equals(" ")) {
                currentStr = "a0";
            }
//            System.out.println(state + " " + pointer.get());
//            System.out.println(String.join("", values));

            table.get(state.get()).get(currentStr).forEach(action -> {
                if (action.equals(">")) {
                    incrementPointer(values, pointer);
                } else if (action.equals("<")) {
                    decrementPointer(values, pointer, shift);
                } else {
                    if (action.charAt(0) == 'q' && action.length() > 1) {
                        state.set(action);
                    } else {
                        values.set(pointer.get(), action);
                    }
                }
            });
        }
        List<String> output = new ArrayList<>(values.stream().map(value -> {
            if (value.equals("a0")) return "_";
            return value;
        }).toList());
        output.add(shift.get(), "[");
        output.add(shift.get() + startLength + 1, "]");
        return String.join("", output);
    }

    private void incrementPointer(List<String> values, AtomicInteger pointer) {
        if (pointer.incrementAndGet() == values.size()) {
            values.add(" ");
        }
    }

    private void decrementPointer(List<String> values, AtomicInteger pointer, AtomicInteger shift) {
        if (pointer.decrementAndGet() == -1) {
            pointer.set(0);
            values.add(0, " ");
            shift.getAndIncrement();
        }
    }

}