import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Day 1, Part 1: " + Day1.part1());
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