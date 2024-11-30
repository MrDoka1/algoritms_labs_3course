package Laba6;

import classes.ReaderCSV;

public class Laba6 {
    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    private static void test1() {
        System.out.println("Test 1:");
        PushDownAutomaton pushDownAutomaton = ReaderCSV.readPushDownAutomaton("Laba6/push-down automaton");
        System.out.println(pushDownAutomaton.solve(""));
        System.out.println(pushDownAutomaton.solve("0011"));
        System.out.println(pushDownAutomaton.solve("00011"));
        System.out.println(pushDownAutomaton.solve("0000011111"));
    }
    private static void test2() {
        System.out.println("Test 2:");
        PushDownAutomaton pushDownAutomaton = ReaderCSV.readPushDownAutomaton("Laba6/push-down automaton 2");
        System.out.println(pushDownAutomaton.solve(""));
        System.out.println(pushDownAutomaton.solve("11"));
    }
    private static void test3() {
        System.out.println("Test 3:");
        PushDownAutomaton pushDownAutomaton = ReaderCSV.readPushDownAutomaton("Laba6/push-down automaton 3");
        System.out.println(pushDownAutomaton.solve(""));
        System.out.println(pushDownAutomaton.solve("1"));
    }
}