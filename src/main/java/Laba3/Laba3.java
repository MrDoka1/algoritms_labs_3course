package Laba3;

import Laba1.DKA;
import classes.ReaderCSV;

import java.util.List;

public class Laba3 {
    public static void main(String[] args) {
        DKA dka = ReaderCSV.readDKA("Laba3/DKA");
        MinimizationDKA minimizationDKA = new MinimizationDKA(dka);
        DKA newDKA = minimizationDKA.start();
        List<String> strings = ReaderCSV.readRows("Laba3/strings");
        for (String string : strings) {
            boolean valid = newDKA.solve(string);
            boolean valid2 = dka.solve(string);
            System.out.println(string + " " + valid2 + " " + valid);
        }
    }
}