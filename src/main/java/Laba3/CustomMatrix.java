package Laba3;

import Laba1.DKA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomMatrix {
    private boolean[][] matrix;
    private final List<String> states;

    public CustomMatrix(DKA dka) {
        final int size = dka.getTransitions().size();
        matrix = new boolean[size][size];
        states = new ArrayList<>(dka.getTransitions().keySet());
    }

    public void setValue(String v1, String v2) {
        matrix[states.indexOf(v1)][states.indexOf(v2)] = true;
        matrix[states.indexOf(v2)][states.indexOf(v1)] = true;
    }

    public boolean getValue(String v1, String v2) {
        return matrix[states.indexOf(v1)][states.indexOf(v2)];
    }

    public boolean[] getColumn(String v) {
        return matrix[states.indexOf(v)];
    }
}