package Laba2;

import classes.ReadCSV;

import java.util.List;

public class Laba2 {
    public static void main(String[] args) {
        NKA nka = ReadCSV.readNKA("/Laba2/NKA");
        List<String> strings = ReadCSV.readRows("Laba2/strings");
        for (String string : strings) {
            boolean answer = nka.solve(string);
            System.out.println(string + ": " + answer);
        }
    }
}