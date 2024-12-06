import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

class Solution {
  public static int[][] offsets = {
    {-1, 0}, // up
    {0, 1},  // right
    {1, 0},  // down
    {0,-1}   // left
  };

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt"));
    String[] grid = input.split("\n"); 
    HashSet<List<Integer>> visited = new HashSet<>();

    int rows = grid.length;
    int cols = grid[0].length();
    
    int guard_x = 0;
    int guard_y = 0;

    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < cols; x++) {
        if (grid[y].charAt(x) == '^') {
          guard_x = x;
          guard_y = y;
          break;
        }
      }
    }

    fillVisitedTiles(grid, visited, guard_x, guard_y, rows, cols);
    System.out.println(visited.size());

    int foundLoops = 0;
    // 0 -> y; 1 -> x
    for (List<Integer> coords : visited) {
      if (coords.get(0) == guard_y && coords.get(1) == guard_x) continue;
      String[] tempGrid = Arrays.copyOf(grid, grid.length);
      char[] row = tempGrid[coords.get(0)].toCharArray();
      row[coords.get(1)] = '#';
      tempGrid[coords.get(0)] = new String(row);

      if (hasLoop(tempGrid, guard_x, guard_y, rows, cols)) foundLoops++; 
    }
    System.out.println(foundLoops);
  }

  private static void fillVisitedTiles(String[] grid, HashSet<List<Integer>> set, int x, int y, int rows, int cols) {
    // 0 = up, 1 = right, 2 = down, 3 = left
    int direction = 0;
    int guard_x = x;
    int guard_y = y;

    while ((guard_x > 0 && guard_x < cols - 1) && (guard_y > 0 && guard_y < rows - 1)) {
      int next_x = guard_x + offsets[direction][1];
      int next_y = guard_y + offsets[direction][0];
      
      if (grid[next_y].charAt(next_x) == '#') {
        direction = (direction + 1) % 4;
      } else {
        guard_x = next_x;
        guard_y = next_y;
        set.add(Arrays.asList(guard_y, guard_x));
      }
    }
  }

  private static boolean hasLoop(String[] grid, int x, int y, int rows, int cols) {
    HashSet<List<Integer>> visited = new HashSet<>();
    int direction = 0;
    int guard_x = x;
    int guard_y = y;

    while ((guard_x > 0 && guard_x < cols - 1) && (guard_y > 0 && guard_y < rows - 1)) {
      int next_x = guard_x + offsets[direction][1];
      int next_y = guard_y + offsets[direction][0];
      
      if (grid[next_y].charAt(next_x) == '#') {
        direction = (direction + 1) % 4;
      } else {
        guard_x = next_x;
        guard_y = next_y;
        List<Integer> coords = Arrays.asList(guard_y, guard_x, direction);
        if (visited.contains(coords)) return true;
        visited.add(coords);
      }
    }
    return false;
  }
}
