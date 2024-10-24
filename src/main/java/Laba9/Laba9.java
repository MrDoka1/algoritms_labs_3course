package Laba9;

import classes.ReaderCSV;
import classes.TXTService;

public class Laba9 {
    public static void main(String[] args) {
        String experiment = "3";

        TuringMachine turingMachine = ReaderCSV.readTuringMachine("Laba9/Turing Machine" + experiment);
        String input = TXTService.readString("Laba9/input" + experiment);
        System.out.println(turingMachine.solveAndCreateFile(input, "Laba9/output"));

    }
}