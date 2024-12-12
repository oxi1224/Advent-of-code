import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

class Solution {
  public static final int[][] offsets = {
    {-1, 0}, // up
    {1, 0}, // down
    {0, -1}, // left
    {0, 1}, // right
  }; 

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();
    
    String[] grid = input.split("\n");
    for (int i = 0; i < grid.length; i++) grid[i] = grid[i].trim();
    int rows = grid.length;
    int cols = grid[0].length();
    // HashSet of (y, x)
    HashSet<List<Integer>> visited = new HashSet<>();
    int totalPrice = 0;

    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < cols; x++) {
        if (visited.contains(Arrays.asList(y, x))) continue;
        totalPrice += findPrice(grid, visited, y, x);
      }
    }
    System.out.println(totalPrice);
  }

  private static int findPrice(String[] grid, HashSet<List<Integer>> visited, int startY, int startX) {
    char group = grid[startY].charAt(startX);
    int rows = grid.length;
    int cols = grid[0].length();

    int perimeter = 0;
    int area = 0;

    Stack<List<Integer>> stack = new Stack<>();
    stack.push(Arrays.asList(startY, startX));
    visited.add(Arrays.asList(startY, startX));
    
    while (!stack.empty()) {
      List<Integer> cell = stack.pop();
      int y = cell.get(0);
      int x = cell.get(1);
      area++;

      for (int[] offset : offsets) {
        int newY = y + offset[0];
        int newX = x + offset[1];
        
        if ((newY < 0 || newY >= rows) && (newX < 0 || newX >= cols)) {
          perimeter += 2;
        } else if (newY < 0 || newY >= rows || newX < 0 || newX >= cols) {
          perimeter++;
        } else if (grid[newY].charAt(newX) != group) {
          perimeter++;
        } else if (!visited.contains(Arrays.asList(newY, newX))) {
          stack.add(Arrays.asList(newY, newX));
          visited.add(Arrays.asList(newY, newX));
        }
      }
    }

    return perimeter * area;
  }
}
