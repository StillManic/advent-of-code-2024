import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day1 {
    public static String part1() {
        List<String> lines = Main.loadFile("day_1/input.txt");

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        lines.stream().map(line -> line.split(" +")).forEach(strings -> {
            left.add(Integer.parseInt(strings[0]));
            right.add(Integer.parseInt(strings[1]));
        });

        left.sort(Comparator.comparingInt(Integer::intValue));
        right.sort(Comparator.comparingInt(Integer::intValue));

        int total = 0;

        for (int i = 0; i < lines.size(); i++) {
            int l = left.get(i);
            int r = right.get(i);

            total += Math.abs(l - r);
        }

        return "" + total;
    }

    public static String part2() {
        List<String> lines = Main.loadFile("day_1/input.txt");

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        lines.stream().map(line -> line.split(" +")).forEach(strings -> {
            left.add(Integer.parseInt(strings[0]));
            right.add(Integer.parseInt(strings[1]));
        });

        long similarity = 0;

        for (int i = 0; i < lines.size(); i++) {
            int l = left.get(i);
            long count = right.stream().filter(r -> r == l).count();

            similarity += l * count;
        }

        return "" + similarity;
    }
}
