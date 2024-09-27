package classes;

import Laba2.NKA;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ReadCSV {
    private final static String PATH = "./src/main/java/";
    public static List<List<String>> readTable(String name) {
        List<List<String>> list = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(PATH + name + ".csv"))) {
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {
                String str = scanner.nextLine();
                List<String> strings = List.of(str.split(","));
                list.add(strings);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

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
        return new NKA(hashMap, trueStates);
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

}