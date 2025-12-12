package solutions;

import util.FileReaderUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class fatihgurkan {

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
}
