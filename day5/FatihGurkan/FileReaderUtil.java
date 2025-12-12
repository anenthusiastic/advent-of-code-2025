package solutions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderUtil {

    public static String[] getLines(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.lines().toArray(String[]::new);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return new String[]{};
    }
}