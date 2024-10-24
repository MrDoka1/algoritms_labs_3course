package classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

public class TXTService {
    private final static String PATH = "./src/main/java/";

    public static String readString(String fileName) {
        try {
            return Files.readString(Paths.get(PATH + fileName + ".txt"));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    public static void writeString(String content, String fileName) {
        try {
            Files.write(Paths.get(PATH + fileName + ".txt"), Collections.singletonList(content));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}