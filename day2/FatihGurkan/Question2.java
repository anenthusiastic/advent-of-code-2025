package solutions;

import util.FileReaderUtil;

import java.util.ArrayList;
import java.util.List;

public class Question2 {

    public long part1() {
        long sum = 0;
        String line = FileReaderUtil.getLines("input2.txt")[0];
        String[] ranges = line.split(",");
        for (String range : ranges) {
            String[] rangeBounds = range.split("-");
            int lowBoundDigitCount = rangeBounds[0].length();
            int highBoundDigitCount = rangeBounds[1].length();

            if (lowBoundDigitCount % 2 == 1 && highBoundDigitCount % 2 == 1) // ikisi de tek sayıda basamaklı
                continue;

            if (lowBoundDigitCount % 2 == 1) {
                int half = highBoundDigitCount / 2;
                String firstHalfHighBoundStr = rangeBounds[1].substring(0, half);
                String lastHalfHighBoundStr = rangeBounds[1].substring(half);

                if (lastHalfHighBoundStr.compareTo(firstHalfHighBoundStr) >= 0) {
                    sum += Long.parseLong(firstHalfHighBoundStr + firstHalfHighBoundStr);
                }
                long halfHighBound = Long.parseLong(firstHalfHighBoundStr);
                long halfLowBound = (long) Math.pow(10, lowBoundDigitCount - half);
                for (long i = halfHighBound - 1; i >= halfLowBound; i--) {
                    sum += i * (long) Math.pow(10, half) + i;
                }
            } else if (highBoundDigitCount % 2 == 1) {
                int half = lowBoundDigitCount / 2;
                String firstHalfLowBoundStr = rangeBounds[0].substring(0, half);
                String lastHalfLowBoundStr = rangeBounds[0].substring(half);

                if (firstHalfLowBoundStr.compareTo(lastHalfLowBoundStr) >= 0) {
                    sum += Long.parseLong(firstHalfLowBoundStr + firstHalfLowBoundStr);
                }
                long halfLowBound = Long.parseLong(firstHalfLowBoundStr);
                long halfHighBound = (long) Math.pow(10, lowBoundDigitCount - half);
                for (long i = halfLowBound + 1; i < halfHighBound; i++) {
                    sum += i * (long) Math.pow(10, half) + i;
                }
            } else {
                int half = lowBoundDigitCount / 2;
                String firstHalfLowBoundStr = rangeBounds[0].substring(0, half);
                String lastHalfLowBoundStr = rangeBounds[0].substring(half);

                if (firstHalfLowBoundStr.compareTo(lastHalfLowBoundStr) >= 0) {
                    sum += Long.parseLong(firstHalfLowBoundStr + firstHalfLowBoundStr);
                }

                String firstHalfHighBoundStr = rangeBounds[1].substring(0, half);
                if (firstHalfLowBoundStr.equals(firstHalfHighBoundStr)) continue;

                String lastHalfHighBoundStr = rangeBounds[1].substring(half);
                if (lastHalfHighBoundStr.compareTo(firstHalfHighBoundStr) >= 0) {
                    sum += Long.parseLong(firstHalfHighBoundStr + firstHalfHighBoundStr);
                }
                long halfLowBound = Long.parseLong(firstHalfLowBoundStr);
                long halfHighBound = Long.parseLong(firstHalfHighBoundStr);
                for (long i = halfLowBound + 1; i < halfHighBound; i++) {
                    sum += i * (long) Math.pow(10, half) + i;
                }
            }
        }
        return sum;
    }

    public long part2() {
        long sum = 0;
        String line = FileReaderUtil.getLines("input2.txt")[0];
        String[] ranges = line.split(",");
        for (String range : ranges) {
            String[] rangeBounds = range.split("-");
            long min = Long.parseLong(rangeBounds[0]);
            long max = Long.parseLong(rangeBounds[1]);
            for (long i = min; i <= max; i++) {
                String numberStr = String.valueOf(i);
                if (isAllDigitsEqual(numberStr)) {
                    sum += i;
                    continue;
                }
                int digitCount = numberStr.length();
                if (isPrime(digitCount))
                    continue;

                List<Integer> divisors = findDivisors(digitCount);
                for (Integer divisor : divisors) {
                    if (existPattern(numberStr, divisor)) {
                        sum += i;
                        break;
                    }
                }
            }
        }
        return sum;
    }

    public boolean isPrime(int digitCount) {
        for (int i = 2; i <= (int) Math.sqrt(digitCount); i++) {
            if (digitCount % i == 0) return false;
        }
        return true;
    }

    public boolean existPattern(String numberStr, Integer divisor) {
        String pattern = numberStr.substring(0, divisor);
        int startIndex = divisor;
        int endIndex = startIndex + divisor;
        while (endIndex <= numberStr.length()) {
            if (!numberStr.substring(startIndex, endIndex).equals(pattern))
                return false;
            startIndex = endIndex;
            endIndex = startIndex + divisor;
        }
        return true;

    }

    public List<Integer> findDivisors(int digitCount) {
        List<Integer> divisors = new ArrayList<>();

        for (int i = 2; i <= digitCount / 2; i++) {
            if (digitCount % i == 0) {
                divisors.add(i);
            }
        }
        return divisors;
    }

    public boolean isAllDigitsEqual(String numberStr) {
        int numberLen = numberStr.length();
        if (numberLen < 2) return false;
        char first = numberStr.charAt(0);
        for (int i = 1; i < numberLen; i++) {
            if (numberStr.charAt(i) != first)
                return false;
        }
        return true;
    }

}
