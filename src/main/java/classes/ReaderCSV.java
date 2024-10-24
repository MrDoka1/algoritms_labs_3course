package classes;

import Laba1.DKA;
import Laba2.NKA;
import Laba9.TuringMachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ReaderCSV {
    private final static String PATH = "./src/main/java/";

    public static List<String> readRows(String name) {
        List<String> list = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(PATH + name + ".csv"))) {
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {
                String str = scanner.nextLine();
                list.add(str);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static NKA readNKA(String fileName) {
        Map<String, Map<String, List<String>>> hashMap = new HashMap<>();
        List<String> trueStates = List.of();

        try (Scanner scanner = new Scanner(new File(PATH + fileName + ".csv"))) {
            scanner.useDelimiter("\n");
            if (scanner.hasNext()) {
                String str = scanner.nextLine();
                trueStates = processingString(str);
            }

            while (scanner.hasNext()) {
                String str = scanner.nextLine();
                List<String> strings = List.of(str.split(","));
                final String state = strings.get(0);
                final String value0 = strings.get(1);
                final String value1 = strings.get(2);
                final String valueE = strings.get(3);
                final List<String> processedValue0 = processingString(value0);
                final List<String> processedValue1 = processingString(value1);
                final List<String> processedValueE = processingString(valueE);

                Map<String, List<String>> transitions = new HashMap<>();
                if (processedValue0 != null) transitions.put("0", processedValue0);
                if (processedValue1 != null) transitions.put("1", processedValue1);
                if (processedValueE != null) transitions.put("E", processedValueE);

                hashMap.put(state, transitions);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new NKA(hashMap, trueStates, "q0");
    }

    public static DKA readDKA(String fileName) {
        Map<String, Map<String, String>> hashMap = new HashMap<>();
        List<String> trueStates = List.of();
        String startState = "";

        try (Scanner scanner = new Scanner(new File(PATH + fileName + ".csv"))) {
            scanner.useDelimiter("\n");
            if (scanner.hasNext()) {
                String str = scanner.nextLine();
                String[] strings = str.split(",");
                startState = strings[0];
                trueStates = processingString(strings[1]);
            }

            while (scanner.hasNext()) {
                String str = scanner.nextLine();
                List<String> strings = List.of(str.split(","));
                final String state = strings.get(0);
                final String value0 = strings.get(1);
                final String value1 = strings.get(2);

                Map<String, String> transitions = new HashMap<>();
                transitions.put("0", value0);
                transitions.put("1", value1);

                hashMap.put(state, transitions);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new DKA(hashMap, trueStates, startState);
    }

    private static List<String> processingString(String string) {
        if (string == null || string.isEmpty() || string.equals("-")) {
            return null;
        }
        if (string.charAt(0) == '[') {
            string = string.substring(1, string.length() - 1);
            return List.of(string.split(" "));
        }
        return List.of(string);
    }

    public static TuringMachine readTuringMachine(String fileName) {
        try (Scanner scanner = new Scanner(new File(PATH + fileName + ".csv"))) {
            scanner.useDelimiter("\n");
            final Map<String, Map<String, List<String>>> table = new HashMap<>();
            final String[] header;

            if (scanner.hasNext()) {
                header = scanner.nextLine().split(",");

                while (scanner.hasNext()) {
                    String[] strings = scanner.nextLine().split(",");
                    Map<String, List<String>> map = new HashMap<>();
                    for (int i = 1; i < strings.length; i++) {
                        map.put(header[i], List.of(strings[i].split(" ")));
                    }
                    table.put(strings[0], map);
                }
                return new TuringMachine(table);
            }
            throw new RuntimeException("Неверный формат csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}