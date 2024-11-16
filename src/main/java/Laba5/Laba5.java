package Laba5;

import classes.ReaderCSV;

import java.util.List;

public class Laba5 {
    public static void main(String[] args) {
        GrammarAnalyzer grammarAnalyzer = new GrammarAnalyzer(ReaderCSV.readGrammar("Laba5/grammar"));
        List<String> stringList = List.of(
                "",

                "<a>" +
                    "<b>" +
                        "<a>dsfsdfsdf</a>" +
                        "<a></a>" +
                    "</b>" +
                "</a>",

                "<a></b>",

                "<a>"

        );
        stringList.forEach(string -> System.out.println(grammarAnalyzer.analyze(string)));
    }
}