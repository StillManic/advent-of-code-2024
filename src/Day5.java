import java.util.*;

public class Day5 {
    private static List<Update> validUpdates = new ArrayList<>();
    private static List<Update> invalidUpdates = new ArrayList<>();
    private static List<Rule> rules = new ArrayList<>();
    private static List<Update> updates = new ArrayList<>();

    public static String part1() {
        List<String> lines = Main.loadFile("day_5/input.txt");

        for (String line : lines) {
            if (line.contains("|")) {
                String[] splitRule = line.split("\\|");
                int before = Integer.parseInt(splitRule[0]);
                int after = Integer.parseInt(splitRule[1]);

                rules.add(new Rule(before, after));
            } else if (line.contains(",")) {
                // parse updates
                String[] splitUpdate = line.split(",");
                List<Integer> values = new ArrayList<>(Arrays.stream(splitUpdate).map(Integer::parseInt).toList());
//                updates.add(new Update(Arrays.stream(splitUpdate).map(Integer::parseInt).toList()));
                updates.add(new Update(values));
            }
        }

        for (Update update : updates) {
            List<Integer> values = update.values;

            boolean isValid = true;

            for (Rule rule : rules) {
                if (!(values.contains(rule.before()) && values.contains(rule.after()))) continue;

                int beforeIndex = values.indexOf(rule.before());
                int afterIndex = values.indexOf(rule.after());

                if (afterIndex < beforeIndex) {
                    isValid = false;
                    invalidUpdates.add(update);
                    update.rulesViolated.add(rule);
                    break;
                }
            }

            if (isValid) {
                validUpdates.add(update);
            }
        }

        int total = 0;

        for (Update update : validUpdates) {
            total += update.values.get((int) Math.floor(update.values.size() / 2.0f));
        }

        return "" + total;
    }

    public static String part2() {
        int sorted = 0;

        do {
            sort();
            sorted = (int) invalidUpdates.stream().filter(u -> u.sorted).count();
        } while (sorted < invalidUpdates.size() - 1);

        int total = 0;

        for (Update update : invalidUpdates) {
//            System.out.println(update);
            total += update.values.get((int) Math.floor(update.values.size() / 2.0f));
        }

        return "" + total;
    }

    private static void sort() {
        for (int u = 0; u < invalidUpdates.size(); u++) {
            Update update = invalidUpdates.get(u);
            update.rulesViolated.clear();

            if (update.sorted) continue;

            boolean isInvalid = false;

            for (Rule rule : rules) {
                if (!(update.values.contains(rule.before()) && update.values.contains(rule.after()))) continue;

                int beforeIndex = update.values.indexOf(rule.before);
                int afterIndex = update.values.indexOf(rule.after);

                if (afterIndex < beforeIndex) {
                    isInvalid = true;
                    update.rulesViolated.add(rule);

                    int oldAfter = update.values.remove(afterIndex);

                    if (beforeIndex == update.values.size()) {
                        update.values.add(beforeIndex, oldAfter);
                    } else {
                        update.values.add(beforeIndex + 1, oldAfter);
                    }
                }
            }

            if (!isInvalid) {
                update.sorted = true;
            }
//            if (isInvalid) {
//                invalidUpdates.add(update);
//                update.rulesViolated.clear();
//            } else {
//                update.sorted = true;
//            }
        }
    }

    private record Rule(int before, int after) {}

    private static class Update {
        public boolean sorted = false;
        public List<Integer> values;
        public List<Rule> rulesViolated = new ArrayList<>();

        public Update(List<Integer> values) {
            this.values = values;
        }

        @Override
        public String toString() {
            return "u: %s       r: %s     sorted: %s".formatted(String.join(", ", values.stream().map(Object::toString).toList()), String.join(", ", rulesViolated.stream().map(r -> "%s|%s".formatted(r.before, r.after)).toList()), sorted);
        }
    }
//    private record Update(List<Integer> values) {}
}
