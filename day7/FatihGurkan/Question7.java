package solutions;

import java.util.*;

public class Question7 {

    public int part1() {
        int sum = 0;
        String[] lines = FileReaderUtil.getLines("input7.txt");
        int enterIndex = lines[0].indexOf("S");
        Set<Integer> beamIndexes = new HashSet<>(Set.of(enterIndex));
        for (int i = 2; i < lines.length; i += 2) {
            List<Integer> lineIndexes = new ArrayList<>();
            int index = lines[i].indexOf("^");
            while (index >= 0) {
                lineIndexes.add(index);
                index = lines[i].indexOf("^", index + 1);
            }
            for (Integer lineIndex : lineIndexes) {
                if (beamIndexes.contains(lineIndex)) {
                    sum += 1;
                    beamIndexes.remove(lineIndex);
                    beamIndexes.add(lineIndex - 1);
                    beamIndexes.add(lineIndex + 1);
                }
            }
        }
        return sum;
    }

    public long part2() {
        long result = 1;
        String[] lines = FileReaderUtil.getLines("input7.txt");
        int enterIndex = lines[0].indexOf("S");
        Map<Integer, Long> beamMap = new HashMap<>();
        beamMap.put(enterIndex, 1L);
        for (int i = 2; i < lines.length; i += 2) {
            List<Integer> lineIndexes = new ArrayList<>();
            int index = lines[i].indexOf("^");
            while (index >= 0) {
                lineIndexes.add(index);
                index = lines[i].indexOf("^", index + 1);
            }

            for (Integer lineIndex : lineIndexes) {
                Long count = beamMap.remove(lineIndex);
                if (count == null) continue;
                beamMap.merge(lineIndex - 1, count, Long::sum);
                beamMap.merge(lineIndex + 1, count, Long::sum);
            }
        }
        return beamMap.values().stream().mapToLong(Long::longValue).sum();
    }
}
