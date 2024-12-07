import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day2 {
    public static String part1() {
        List<String> reports = Main.loadFile("day_2/input.txt");

        int numSafe = 0;

        for (String report : reports) {
            int[] levels = Arrays.stream(report.split(" +")).mapToInt(Integer::parseInt).toArray();

            if (isSafe(levels)) {
                numSafe++;
            }
        }

        return "" + numSafe;
    }

    public static String part2() {
        List<String> reports = Main.loadFile("day_2/input.txt");

        int numSafe = 0;

        for (String report : reports) {
            int[] levels = Arrays.stream(report.split(" +")).mapToInt(Integer::parseInt).toArray();

            if (checkSafety(levels)) {
                numSafe++;
            }
        }

        return "" + numSafe;
    }

    private static boolean checkSafety(int[] levels) {
        boolean isSafe = isSafe(levels);

        if (!isSafe) {
            for (int i = 0; i < levels.length; i++) {
                int[] dampened = dampen(levels, i);
                isSafe = isSafe(dampened);
                if (isSafe) {
                    break;
                }
            }
        }

        return isSafe;
    }

    private static boolean isSafe(int[] levels) {
        int increaseFlag = 0;

        for (int i = 0; i < levels.length - 1; i++) {
            int level = levels[i];
            int nextLevel = levels[i + 1];
            int difference = Math.abs(nextLevel - level);

            if (increaseFlag > 0 && nextLevel < level) {
                return false;
            } else if (increaseFlag < 0 && nextLevel > level) {
                return false;
            } else if (nextLevel == level) {
                return false;
            } else if (difference > 3) {
                return false;
            }

            if (nextLevel > level) increaseFlag = 1;
            else if (nextLevel < level) increaseFlag = -1;
        }

        return true;
    }

    private static int[] dampen(int[] levels, int index) {
        return IntStream.range(0, levels.length).filter(i -> i != index).map(i -> levels[i]).toArray();
    }
}
