package solutions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Question6 {

    public int part1() {
        List<Range> ranges = new ArrayList<>();
        String[] lines = FileReaderUtil.getLines("input5.txt");
        int lineIndex = 0;
        String line = lines[lineIndex];
        while (line != null && !line.trim().isEmpty()) {
            String[] parts = line.split("-");
            long start = Long.parseLong(parts[0].trim());
            long end = Long.parseLong(parts[1].trim());
            ranges.add(new Range(start, end));
            line = lines[++lineIndex];
        }

        List<Long> values = new ArrayList<>();
        lineIndex++;
        while (lineIndex<lines.length) {
            line = lines[lineIndex];
            values.add(Long.parseLong(line.trim()));
            lineIndex++;
        }


        int count = 0;
        for (Long value : values) {
            for (Range range : ranges) {
                if (range.contains(value)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    public long part2() {
        List<Range> ranges = new ArrayList<>();
        String[] lines = FileReaderUtil.getLines("input5.txt");
        int lineIndex = 0;
        String line = lines[lineIndex];
        while (line != null && !line.trim().isEmpty()) {
            String[] parts = line.split("-");
            long start = Long.parseLong(parts[0].trim());
            long end = Long.parseLong(parts[1].trim());
            ranges.add(new Range(start, end));
            line = lines[++lineIndex];
        }

        Set<Integer> mergedIndexes = new HashSet<>();
        for (int i = 0; i < ranges.size(); i++) {
            Range range1 = ranges.get(i);
            boolean flag = false;
            for (int j = i + 1; j < ranges.size(); j++) {
                Range range2 = ranges.get(j);
                if (range1.start() <= range2.end() && range2.start() <= range1.end()) {
                    long start = Math.min(range1.start(), range2.start());
                    long end = Math.max(range1.end(), range2.end());
                    ranges.set(j, new Range(start, end));
                    mergedIndexes.add(j);
                    mergedIndexes.remove(i);
                    flag = true;
                    break;
                }
            }
            if (!flag) mergedIndexes.add(i);
        }

        long count = 0;
        for (Integer index : mergedIndexes) {
            Range r = ranges.get(index);
            count += r.end() - r.start() + 1;
        }
        return count;
    }
}


