package Laba1;

import classes.ReadCSV;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Laba1 {
    public static void main(String[] args) {
        List<List<String>> table = ReadCSV.readTable("Laba1/DKA");
        List<String> trueStates = table.get(0);
        HashMap<String, List<String>> state_valueState = new HashMap<>();

        for (int i = 1; i < table.size(); i++) {
            List<String> row = table.get(i);
            List<String> statesList = Arrays.asList(row.get(1), row.get(2).replace("\r", "").replace("\n", ""));
            state_valueState.put(row.get(0), statesList);
        }

        List<String> strings = ReadCSV.readRows("Laba1/strings");
        for (String string : strings) {
            String[] values = string.split("");
            String currentState = "q0";
            for (String value : values) {
//                System.out.println(currentState + " " + values.get(j));
                if (value.equals("0")) {
                    currentState = state_valueState.get(currentState).get(0);
                } else {
                    currentState = state_valueState.get(currentState).get(1);
                }
            }
            boolean valid = trueStates.contains(currentState + "\r");
            System.out.println(string + " " + valid);
        }

    }
}