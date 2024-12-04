import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Solution {
  public static int[][] neighbour_offsets = {
    {-1, -1}, // up-left
    {-1, 0},  // up
    {-1, 1},  // up-right
    {0, -1},  // left
    {0, 1},   // right
    {1, -1},  // down-left
    {1, 0},   // down
    {1, 1}    // down-right
  };

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt"));
    String[] grid = input.split("\n");

    int cols = grid.length;
    int rows = grid[0].length();
    int xmasCount = 0;

    for (int y = 0; y < cols; y++) {
      for (int x = 0; x < rows; x++) {
        if (grid[y].charAt(x) != 'X') continue;
        for (int[] offset : neighbour_offsets) {
          if (is_xmas(grid, x, y, offset[1], offset[0], rows, cols)) xmasCount++;
        }
      }
    }
    System.out.println(xmasCount);
  }

  private static boolean is_xmas(String[] grid, int x, int y, int dx, int dy, int rows, int cols) {
    String target = "XMAS";

    for (int i = 0; i < target.length(); i++) {
      int newY = y + dy * i;
      int newX = x + dx * i;

      if (newX < 0 || newX >= rows || newY < 0 || newY >= cols) return false;
      if (grid[newY].charAt(newX) != target.charAt(i)) return false;
    }
    return true;
  }
}
