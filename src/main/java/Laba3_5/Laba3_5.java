package Laba3_5;

import Laba1.DKA;
import Laba2.NKA;
import classes.ReaderCSV;

import java.util.List;

public class Laba3_5 {
    public static void main(String[] args) {
        NKA nka = ReaderCSV.readNKA("/Laba2/NKA");
        DKA dka = ThompsonAlgorithm.start(nka);

        List<String> strings = ReaderCSV.readRows("Laba2/strings");
        for (String string : strings) {
            boolean answer = nka.solve(string);
            boolean answer2 = dka.solve(string);
            System.out.println(string + ": " + answer + " " + answer2);
        }
    }
}