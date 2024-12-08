import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Day 1, Part 1: " + Day1.part1());
        System.out.println("Day 1, Part 2: " + Day1.part2());
        System.out.println("Day 2, Part 1: " + Day2.part1());
        System.out.println("Day 2, Part 2: " + Day2.part2());
        System.out.println("Day 3, Part 1: " + Day3.part1());
        System.out.println("Day 3, Part 2: " + Day3.part2());
        System.out.println("Day 4, Part 1: " + Day4.part1());
        System.out.println("Day 4, Part 2: " + Day4.part2());
    }

    public static List<String> loadFile(String fileName) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream stream = loader.getResourceAsStream(fileName)) {
            byte[] bytes = stream.readAllBytes();
            ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length);
            new ByteArrayInputStream(bytes).transferTo(baos);
            String contents = baos.toString();
            return Arrays.asList(contents.split("\r\n"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}