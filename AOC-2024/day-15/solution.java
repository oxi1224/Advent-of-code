import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class Solution {
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();
    String[] lines = input.split("\n");

    ArrayList<char[]> grid = new ArrayList<>();
    ArrayList<Character> moves = new ArrayList<>();
    boolean readingMoves = false;
    int[] robot = new int[]{0, 0};

    for (int i = 0; i < lines.length; i++) {
      if (lines[i].trim().isBlank()) {
        readingMoves = true;
        continue;
      }

      if (!readingMoves) {
        grid.add(lines[i].trim().toCharArray());
        for (int j = 0; j < grid.get(i).length; j++) {
          if (grid.get(i)[j] == '@') {
            robot = new int[]{i, j};
            break;
          }
        }
        continue;
      }
      
      for (char c : lines[i].trim().toCharArray()) moves.add(c);
    }

    for (char move : moves) {
      int[] offset = getOffset(move);
      int y = robot[0] + offset[0];
      int x = robot[1] + offset[1];

      if (grid.get(y)[x] == '#') {
        continue;
      } else if (grid.get(y)[x] == '.') {
        grid.get(robot[0])[robot[1]] = '.';
        grid.get(y)[x] = '@';
        robot[0] = y;
        robot[1] = x;
      } else if (grid.get(y)[x] == 'O') {
        int boxCount = 1;
        int boxY = y + offset[0];
        int boxX = x + offset[1];
        while (grid.get(boxY)[boxX] == 'O') {
          boxCount++;
          boxY += offset[0];
          boxX += offset[1];
        }
        
        // If character at last box y + offset and last box x + offset is equal to #
        if (grid.get(y + offset[0] * boxCount)[x + offset[1] * boxCount] == '#') continue;

        for (int box = 1; box <= boxCount; box++) {
          int newY = y + offset[0] * box;
          int newX = x + offset[1] * box;
          grid.get(newY)[newX] = 'O';
        }

        grid.get(robot[0])[robot[1]] = '.';
        grid.get(y)[x] = '@';
        robot[0] = y;
        robot[1] = x;
      }
    }

    int rows = grid.size();
    int cols = grid.get(0).length;
    long GPSsum = 0;
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < cols; x++) {
        if (grid.get(y)[x] == 'O') GPSsum += 100L * y + x;
      }
    }
    System.out.println(GPSsum);
  }

  private static int[] getOffset(char c) {
    if (c == '^') return new int[]{-1, 0};
    if (c == 'v') return new int[]{1, 0};
    if (c == '<') return new int[]{0, -1};
    if (c == '>') return new int[]{0, 1};
    return new int[]{-1, -1};
  }
}
