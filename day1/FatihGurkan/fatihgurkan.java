package solutions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Question1 {

    public int question1() {
        int zeroCount = 0;
        int currentPoint = 50;
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                int step = Integer.parseInt(line.substring(1));
                zeroCount += step / 100;
                step = step % 100;
                if (line.charAt(0) == 'L') {
                    int distance = currentPoint == 0 ? 100 : (currentPoint + 100) % 100;
                    if (distance <= step) {
                        zeroCount++;
                    }
                    currentPoint = (currentPoint - step) % 100;
                } else if (line.charAt(0) == 'R') {
                    int distance = currentPoint == 0 ? 100 : (100 - currentPoint) % 100;
                    if (distance <= step) {
                        zeroCount++;
                    }
                    currentPoint = (currentPoint + step) % 100;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return zeroCount;
    }
}
