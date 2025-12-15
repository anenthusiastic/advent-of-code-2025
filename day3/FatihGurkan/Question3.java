package solutions;

public class Question3 {

    private final int digitCount = 12;

    public long solution() {
        String[] lines = FileReaderUtil.getLines("input3.txt");
        long sum = 0;

        for (String line : lines) {
            sum += findTheMax(line, '9', new StringBuilder());
        }
        return sum;
    }

    private Long findTheMax(String line, char number, StringBuilder currentMaxNumber) {

        int indexOfMax = line.indexOf(number);
        if (indexOfMax == -1)
            return findTheMax(line, (char) (number - 1), currentMaxNumber);

        currentMaxNumber.append(number);

        if (currentMaxNumber.length() == digitCount) {
            return Long.parseLong(currentMaxNumber.toString());
        }

        if (line.length() - indexOfMax - 1 == digitCount - currentMaxNumber.length()) {
            currentMaxNumber.append(line.substring(indexOfMax + 1));
            return Long.parseLong(currentMaxNumber.toString());
        }

        if (line.length() - indexOfMax - 1 < digitCount - currentMaxNumber.length()) {
            currentMaxNumber.deleteCharAt(currentMaxNumber.length() - 1);
            return findTheMax(line, (char) (number - 1), currentMaxNumber);
        }

        return findTheMax(line.substring(indexOfMax + 1), '9', currentMaxNumber);

    }
}
