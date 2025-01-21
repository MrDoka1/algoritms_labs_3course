package Laba7;

import Laba6.PushDownAutomaton;
import classes.ReaderCSV;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Laba7Tests {
    @Test
    public void test1() {
        HashMap<String, List<String>> grammar = ReaderCSV.readGrammar("/Laba7/grammar");
        GrammarToPushDownAutomaton grammarToPushDownAutomaton = new GrammarToPushDownAutomaton(grammar);
        PushDownAutomaton pda = grammarToPushDownAutomaton.getPushDownAutomaton();
        assertTrue(pda.solve(""));
        assertTrue(pda.solve("()()"));
        assertTrue(pda.solve("((()))()"));
        assertFalse(pda.solve("((())))()"));
        assertFalse(pda.solve("(((()))()"));
    }

    @Test
    public void test2() {
        HashMap<String, List<String>> grammar = ReaderCSV.readGrammar("/Laba7/grammar2");
        GrammarToPushDownAutomaton grammarToPushDownAutomaton = new GrammarToPushDownAutomaton(grammar);
        PushDownAutomaton pda = grammarToPushDownAutomaton.getPushDownAutomaton();
        assertTrue(pda.solve("<a></a>"));
        assertTrue(pda.solve("<a><b>ababababab</b></a>"));
        assertTrue(pda.solve("<a><b>ababababab</b><a></a></a>"));
        assertFalse(pda.solve("<a></b>"));
        assertFalse(pda.solve("<a>"));
        assertFalse(pda.solve("abababab"));
    }
}