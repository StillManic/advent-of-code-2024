import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    public static String part1() {
        List<String> lines = Main.loadFile("day_3/input.txt");

        int total = 0;

        for (String line : lines) {
            Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                int left = Integer.parseInt(matcher.group(1));
                int right = Integer.parseInt(matcher.group(2));
                total += (left * right);
            }
        }

        return "" + total;
    }

    public static String part2() {
        List<String> lines = Main.loadFile("day_3/input.txt");

        Map<Integer, Operation> operations = new HashMap<>();

        int total = 0;

        for (String line : lines) {
            Pattern mulPattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
            Matcher mulMatcher = mulPattern.matcher(line);

            while (mulMatcher.find()) {
                operations.put(mulMatcher.start(), new Operation(new Mul(mulMatcher.group(1), mulMatcher.group(2)), null));
            }

            Pattern doPattern = Pattern.compile("do\\(\\)");
            Matcher doMatcher = doPattern.matcher(line);

            while (doMatcher.find()) {
                operations.put(doMatcher.start(), new Operation(null, new DoOrDont(true)));
            }

            Pattern dontPattern = Pattern.compile("don't\\(\\)");
            Matcher dontMatcher = dontPattern.matcher(line);

            while (dontMatcher.find()) {
                operations.put(dontMatcher.start(), new Operation(null, new DoOrDont(false)));
            }

            List<Integer> operationsKeys = operations.keySet().stream().sorted().toList();

            boolean enabled = true;

            for (int i : operationsKeys) {
                Operation operation = operations.get(i);

                if (enabled && operation.mul() != null) {
                    total += (Integer.parseInt(operation.mul().left()) * (Integer.parseInt(operation.mul().right())));
                } else if (operation.doOrDont() != null) {
                    enabled = operation.doOrDont().enable();
                }
            }
        }

        return "" + total;
    }

    private record Mul(String left, String right) {}
    private record DoOrDont(boolean enable) {}
    private record Operation(Mul mul, DoOrDont doOrDont) {};
}
