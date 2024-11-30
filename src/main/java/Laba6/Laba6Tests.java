package Laba6;

import classes.ReaderCSV;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Laba6Tests {
    @Test
    public void test1() {
        PushDownAutomaton pDA = ReaderCSV.readPushDownAutomaton("Laba6/push-down automaton");
        assertTrue(pDA.solve(""));
        assertTrue(pDA.solve("0011"));
        assertFalse(pDA.solve("00011"));
        assertTrue(pDA.solve("0000011111"));
    }
    @Test
    public void test2() {
        PushDownAutomaton pDA = ReaderCSV.readPushDownAutomaton("Laba6/push-down automaton 2");
        assertTrue(pDA.solve(""));
        assertTrue(pDA.solve("11"));
        assertTrue(pDA.solve("1011001101"));
    }
    @Test
    public void test3() {
        PushDownAutomaton pDA = ReaderCSV.readPushDownAutomaton("Laba6/push-down automaton 3");
        assertTrue(pDA.solve(""));
        assertTrue(pDA.solve("1"));
        assertTrue(pDA.solve("101"));
        assertTrue(pDA.solve("0110"));
        assertTrue(pDA.solve("1011001001101"));
    }
}