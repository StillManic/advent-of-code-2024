import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 {
    static char[][] grid;
    static List<Obstacle> obstacles = new ArrayList<>();
    static int startingRow, startingCol;

    public static String part1() {
        List<String> lines = Main.loadFile("day_6/input.txt");

        grid = new char[lines.size()][lines.get(0).length()];

        for (int row = 0; row < lines.size(); row++) {
            String line = lines.get(row);

            for (int col = 0; col < line.length(); col++) {
                grid[row][col] = line.charAt(col);
            }
        }

        return "" + walk(grid, false).size();
    }

    public static String part2() {
        List<String> lines = Main.loadFile("day_6/input.txt");

        grid = new char[lines.size()][lines.get(0).length()];

        for (int row = 0; row < lines.size(); row++) {
            String line = lines.get(row);

            for (int col = 0; col < line.length(); col++) {
                grid[row][col] = line.charAt(col);
            }
        }

        walk(grid, true);

        obstacles = obstacles.stream().distinct().toList();

        return "" + obstacles.size();
    }

    private static List<Visited> walk(char[][] grid, boolean recurse) {
        int currRow = -1, currCol = -1, rowOffset = -1, colOffset = 0;
        char guard = '^';

        // Find initial positions
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                char space = grid[row][col];

                if (space == '^') {
                    startingRow = row;
                    startingCol = col;
                    currRow = row;
                    currCol = col;
                }
            }
        }

        // Play the game
        List<Visited> visiteds = new ArrayList<>();
        visiteds.add(new Visited(currRow, currCol, guard));

        boolean insideGrid = true;

        do {
            grid[currRow][currCol] = '*';

            currRow += rowOffset;
            currCol += colOffset;

            insideGrid = (currRow >= 0) && (currCol >= 0) && (currRow < grid.length) && (currCol < grid[currRow].length);

            if (!insideGrid) break;

            if (recurse) {
                if (currRow == startingRow && currCol == startingCol) continue;

                char[][] newGrid = new char[grid.length][grid[0].length];

                for (int r = 0; r < newGrid.length; r++) {
                    for (int c = 0; c < newGrid[r].length; c++) {
                        if (grid[r][c] == '#') {
                            newGrid[r][c] = '#';
                        } else {
                            newGrid[r][c] = '.';
                        }
                    }
                }

                newGrid[startingRow][startingCol] = '^';

                newGrid[currRow][currCol] = '#';

                List<Visited> newWalk = walk(newGrid, false);

                if (newWalk == null) {
                    obstacles.add(new Obstacle(currRow, currCol));
                }
            }

            char newSpace = grid[currRow][currCol];

            if (newSpace == '.') {
                grid[currRow][currCol] = guard;
                visiteds.add(new Visited(currRow, currCol, guard));
            } else if (newSpace == '*') {
                // already visited this square, check if we're in a loop (travelling the same direction as last time)
                Visited visited = new Visited(currRow, currCol, guard);
                if (visiteds.contains(visited)) {
                    return null;
                }
            } else if (newSpace == '#') {
                currRow -= rowOffset;
                currCol -= colOffset;

                switch (guard) {
                    case '^':
                        rowOffset = 0;
                        colOffset = 1;
                        guard = '>';
                        break;
                    case '>':
                        rowOffset = 1;
                        colOffset = 0;
                        guard = 'v';
                        break;
                    case 'v':
                        rowOffset = 0;
                        colOffset = -1;
                        guard = '<';
                        break;
                    case '<':
                        rowOffset = -1;
                        colOffset = 0;
                        guard = '^';
                        break;
                    default:
                        System.err.println("Not sure how you started going that way!");
                }
            }
        } while (insideGrid);

        return visiteds;
    }

    private record Obstacle(int row, int col) {}
    private record Visited(int row, int col, char direction) {}
}
