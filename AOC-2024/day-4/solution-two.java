import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Solution {
  public static int[][] neighbour_offsets = {
    {-1, -1}, // up-left
    {-1, 1},  // up-right
    {1, -1},  // down-left
    {1, 1}    // down-right
  };

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt"));
    String[] grid = input.split("\n");

    int cols = grid.length;
    int rows = grid[0].length();
    int xMasCount = 0;

    for (int y = 0; y < cols; y++) {
      for (int x = 0; x < rows; x++) {
        if (grid[y].charAt(x) != 'A') continue;
        if (is_x_mas(grid, x, y, rows, cols)) xMasCount++;
      }
    }
    System.out.println(xMasCount);
  }

  private static boolean is_x_mas(String[] grid, int x, int y, int rows, int cols) {
    for (int[] offset : neighbour_offsets) {
      int newY = y + offset[0];
      int newX = x + offset[1];
      if (newX < 0 || newX >= rows || newY < 0 || newY >= cols) return false;
    }

    String temp = "" + grid[y - 1].charAt(x - 1) + grid[y].charAt(x) + grid[y + 1].charAt(x + 1);
    if (!temp.equals("MAS") && !temp.equals("SAM")) return false;
    temp = "" + grid[y - 1].charAt(x + 1) + grid[y].charAt(x) + grid[y + 1].charAt(x - 1);
    if (!temp.equals("MAS") && !temp.equals("SAM")) return false;
    return true;
  }
}
