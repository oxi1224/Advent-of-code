import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

class Solution {
  public static int[][] offsets = {
    {-1, 0}, // up
    {0,  1}, // right
    {1,  0}, // down
    {0, -1}  // left
  };

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt"));
    String[] grid = input.split("\n"); 
    HashSet<List<Integer>> visited = new HashSet<>();

    int rows = grid.length;
    int cols = grid[0].length();
    
    // 0 = up, 1 = right, 2 = down, 3 = left
    int direction = 0;
    int guard_x = 0;
    int guard_y = 0;

    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < cols; x++) {
        if (grid[y].charAt(x) == '^') {
          guard_x = x;
          guard_y = y;
        }
      }
    }

    while ((guard_x > 0 && guard_x < cols - 1) && (guard_y > 0 && guard_y < rows - 1)) {
      int next_x = guard_x + offsets[direction][1];
      int next_y = guard_y + offsets[direction][0];
      
      if (grid[next_y].charAt(next_x) == '#') {
        direction = (direction + 1) % 4;
      } else {
        guard_x = next_x;
        guard_y = next_y;
        visited.add(Arrays.asList(guard_x, guard_y));
      }
    }

    System.out.println(visited.size());
  }
}
