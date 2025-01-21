package Laba7;

import Laba6.PushDownAutomaton;
import classes.ReaderCSV;

import java.util.HashMap;
import java.util.List;

public class Laba7 {
    public static void main(String[] args) {
        /*HashMap<String, List<String>> grammar = ReaderCSV.readGrammar("/Laba7/grammar");
        GrammarToPushDownAutomaton grammarToPushDownAutomaton = new GrammarToPushDownAutomaton(grammar);
        PushDownAutomaton pda = grammarToPushDownAutomaton.getPushDownAutomaton();
        System.out.println(pda.solve(""));
        System.out.println(pda.solve("()()"));
        System.out.println(pda.solve("((()))()"));*/
        HashMap<String, List<String>> grammar = ReaderCSV.readGrammar("/Laba7/grammar2");
        GrammarToPushDownAutomaton grammarToPushDownAutomaton = new GrammarToPushDownAutomaton(grammar);
        PushDownAutomaton pda = grammarToPushDownAutomaton.getPushDownAutomaton();
        System.out.println(pda.solve("<a></a>"));
    }
}