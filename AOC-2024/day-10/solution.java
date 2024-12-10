import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

class Solution {
  private static int[][] directions = {
    {-1, 0}, // up
    {1, 0}, // down
    {0, -1}, // left 
    {0, 1} // right 
  };

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt"));
    String[] grid = input.split("\n");
    
    int rows = grid.length;
    int cols = grid[0].length();

    int totalScore = 0;

    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < cols; x++) {
        if (grid[y].charAt(x) - '0' == 0) {
          HashSet<String> visited = new HashSet<>();
          visited.add(y + "," + x);
          totalScore += dfs(grid, visited, y, x); 
        }
      }
    }

    System.out.println(totalScore);
  }

  private static int dfs(String[] grid, HashSet<String> visited, int y, int x) {
    int rows = grid.length;
    int cols = grid[0].length();
    int score = 0;
    int height = grid[y].charAt(x) - '0';

    for (int[] dir : directions) {
      int newY = y + dir[0];
      int newX = x + dir[1];

      if (newY < 0 || newY >= rows || newX < 0 || newX >= cols) continue;
      if (visited.contains(newY + "," + newX)) continue;
      int nextHeight = grid[newY].charAt(newX) - '0';
      
      if (nextHeight != height + 1) continue;
      visited.add(newY + "," + newX);

      if (nextHeight == 9) {
        score++;
      } else {
        score += dfs(grid, visited, newY, newX);
      }
    }

    return score;
  }
}
