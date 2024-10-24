package Laba4;

import Laba1.DKA;
import Laba2.NKA;
import Laba3.MinimizationDKA;
import Laba3_5.ThompsonAlgorithm;
import classes.PrinterKA;
import classes.ReaderCSV;

import java.util.List;

public class Laba4 {
    public static void main(String[] args) {
//        NKA nka = ParserRegExp.start("(110)(11)");
//        NKA nka = ParserRegExp.start("(110)(101)*");
//        NKA nka = ParserRegExp.start("((110)(11))*");
//        NKA nka = ParserRegExp.start("(001)+(110)(101)*");
//        NKA nka = ParserRegExp.start("(001)+(110)(101)(101)*");
        NKA nka = ParserRegExp.start("(001)+(110)((101)+(0)+(1))*"); /*Интересный пример*/
//        PrinterKA.print(nka);

        List<String> strings = ReaderCSV.readRows("Laba4/strings");
        for (String string : strings) {
            boolean answer = nka.solve(string);
            System.out.println(string + ": " + answer);
        }

    }
}