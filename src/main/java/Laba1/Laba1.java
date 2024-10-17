package Laba1;

import classes.ReaderCSV;

import java.util.List;

public class Laba1 {
    public static void main(String[] args) {
        DKA dka = ReaderCSV.readDKA("Laba1/DKA");
        List<String> strings = ReaderCSV.readRows("Laba1/strings");
        for (String string : strings) {
            boolean valid = dka.solve(string);
            System.out.println(string + " " + valid);
        }
    }
}