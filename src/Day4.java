import java.util.ArrayList;
import java.util.List;

public class Day4 {
    public static String part1() {
        List<String> lines = Main.loadFile("day_4/input.txt");

        char[][] letters = new char[lines.size()][lines.get(0).length()];

        int total = 0;

        for (int row = 0; row < lines.size(); row++) {
            String line = lines.get(row);

            for (int col = 0; col < line.length(); col++) {
                letters[row][col] = line.charAt(col);
            }
        }

        for (int row = 0; row < letters.length; row++) {
            for (int col = 0; col < letters[0].length; col++) {
                char current = letters[row][col];

                if (current == 'X') {
                    List<String> allDirections = getAllDirections(letters, row, col);

                    for (String word : allDirections) {
                        if (word.equals("XMAS")) {
                            total++;
                        }
                    }
                }
            }
        }

        return "" + total;
    }

    private static List<String> getAllDirections(char[][] letters, int r, int c) {
        List<String> allDirections = new ArrayList<>();

        int height = letters.length;
        int width = letters[0].length;

        for (int dir = 0; dir < 8; dir++) {
            StringBuilder builder = new StringBuilder("X");

            switch (dir) {
                case 0: // east
                    if (c + 3 >= width) continue;
                    builder.append(letters[r][c + 1]);
                    builder.append(letters[r][c + 2]);
                    builder.append(letters[r][c + 3]);
                    break;
                case 1: // south-east
                    if ((c + 3 >= width) || (r + 3 >= height)) continue;
                    builder.append(letters[r + 1][c + 1]);
                    builder.append(letters[r + 2][c + 2]);
                    builder.append(letters[r + 3][c + 3]);
                    break;
                case 2: // south
                    if (r + 3 >= height) continue;
                    builder.append(letters[r + 1][c]);
                    builder.append(letters[r + 2][c]);
                    builder.append(letters[r + 3][c]);
                    break;
                case 3: // south-west
                    if ((c - 3 < 0) || (r + 3 >= height)) continue;
                    builder.append(letters[r + 1][c - 1]);
                    builder.append(letters[r + 2][c - 2]);
                    builder.append(letters[r + 3][c - 3]);
                    break;
                case 4: // west
                    if (c - 3 < 0) continue;
                    builder.append(letters[r][c - 1]);
                    builder.append(letters[r][c - 2]);
                    builder.append(letters[r][c - 3]);
                    break;
                case 5: // north-west
                    if ((c - 3 < 0) || (r - 3 < 0)) continue;
                    builder.append(letters[r - 1][c - 1]);
                    builder.append(letters[r - 2][c - 2]);
                    builder.append(letters[r - 3][c - 3]);
                    break;
                case 6: // north
                    if (r - 3 < 0) continue;
                    builder.append(letters[r - 1][c]);
                    builder.append(letters[r - 2][c]);
                    builder.append(letters[r - 3][c]);
                    break;
                case 7: // north-east
                    if ((c + 3 >= width) || (r - 3 < 0)) continue;
                    builder.append(letters[r - 1][c + 1]);
                    builder.append(letters[r - 2][c + 2]);
                    builder.append(letters[r - 3][c + 3]);
                    break;
            }

            allDirections.add(builder.toString());
        }

        return allDirections;
    }

    public static String part2() {
        List<String> lines = Main.loadFile("day_4/input.txt");

        char[][] letters = new char[lines.size()][lines.get(0).length()];

        int total = 0;

        for (int row = 0; row < lines.size(); row++) {
            String line = lines.get(row);

            for (int col = 0; col < line.length(); col++) {
                letters[row][col] = line.charAt(col);
            }
        }
        
        return "" + findAllX_MASes(letters);
    }

    private static int findAllX_MASes(char[][] letters) {
        int total = 0;

        for (int r = 0; r < letters.length - 2; r++) {
            for (int c = 0; c < letters[0].length - 2; c++) {
                char topLeft = letters[r][c];
                char topRight = letters[r][c + 2];
                char middle = letters[r + 1][c + 1];
                char bottomRight = letters[r + 2][c + 2];
                char bottomLeft = letters[r + 2][c];

                if (middle == 'A') {
                    if (topLeft == 'M') {
                        if (topRight == 'M') {
                            if (bottomLeft == 'S' && bottomRight == 'S') {
                                total++;
                            }
                        } else if (bottomLeft == 'M') {
                            if (topRight == 'S' && bottomRight == 'S') {
                                total++;
                            }
                        }
                    } else if (topLeft == 'S') {
                        if (topRight == 'S') {
                            if (bottomLeft == 'M' && bottomRight == 'M') {
                                total++;
                            }
                        } else if (bottomLeft == 'S') {
                            if (topRight == 'M' && bottomRight == 'M') {
                                total++;
                            }
                        }
                    }
                }
            }
        }

        return total;
    }
}
