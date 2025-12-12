package solutions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Question6 {


    public long part1() {
        long sum = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("input6.txt"))) {
            String line;
            List<String[]> lineNumbersList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] lineNumbers = line.trim().split("\\s+");
                if (lineNumbers[0].equals("+") || lineNumbers[0].equals("*")) {
                    for (int i = 0; i < lineNumbers.length; i++) {
                        if (lineNumbers[i].equals("+")) {
                            for (String[] numbers : lineNumbersList) {
                                sum += Long.parseLong(numbers[i]);
                            }
                        } else if (lineNumbers[i].equals("*")) {
                            long multiplication = 1;
                            for (String[] numbers : lineNumbersList) {
                                multiplication *= Long.parseLong(numbers[i]);
                            }
                            sum += multiplication;
                        }
                    }
                }
                lineNumbersList.add(lineNumbers);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return sum;
    }

    public long part2() {
        long sum = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("input6.txt"))) {
            List<String> lines = reader.lines().collect(Collectors.toCollection(ArrayList::new));
            int lineLength = lines.get(0).length();
            char operator = lines.get(lines.size() - 1).charAt(0);
            List<Long> numbersToBeOperated = new ArrayList<>();
            for (int i = 0; i < lineLength; i++) {
                StringBuilder column = new StringBuilder();
                for (String line : lines) {
                    column.append(line.charAt(i));
                }

                if (column.toString().isBlank()) {
                    sum += calculate(numbersToBeOperated, operator);
                    numbersToBeOperated.clear();
                } else if (i == lineLength - 1) {
                    numbersToBeOperated.add(Long.parseLong(column.toString().trim()));
                    sum += calculate(numbersToBeOperated, operator);
                } else {
                    char lastChar = column.charAt(column.length() - 1);
                    if (lastChar == '+' | lastChar == '*') {
                        operator = lastChar;
                        column.deleteCharAt(column.length() - 1);
                    }
                    numbersToBeOperated.add(Long.parseLong(column.toString().trim()));
                }

            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return sum;
    }

    private long calculate(List<Long> numbersToBeOperated, char operator) {
        if (operator == '+') {
            return numbersToBeOperated.stream().mapToLong(Long::longValue).sum();
        }
        return numbersToBeOperated.stream().mapToLong(Long::longValue).reduce(1L, (a, b) -> a * b);
    }
}
