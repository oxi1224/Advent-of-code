import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    String input = Files.readString(Paths.get("example.txt")).trim();
    
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

    // System.out.println(totalPrice);
  }

  private static int findPrice(String[] grid, HashSet<List<Integer>> visited, int startY, int startX) {
    char group = grid[startY].charAt(startX);
    int rows = grid.length;
    int cols = grid[0].length();

    int area = 0;
    ArrayList<List<Integer>> perimeterBlocks = new ArrayList<>();
    int sideCount = 0;

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
          if (!perimeterBlocks.contains(Arrays.asList(y, x))) perimeterBlocks.add(Arrays.asList(y, x));
        } else if (newY < 0 || newY >= rows || newX < 0 || newX >= cols) {
          if (!perimeterBlocks.contains(Arrays.asList(y, x))) perimeterBlocks.add(Arrays.asList(y, x));
        } else if (grid[newY].charAt(newX) != group) {
          if (!perimeterBlocks.contains(Arrays.asList(y, x))) perimeterBlocks.add(Arrays.asList(y, x));
        } else if (!visited.contains(Arrays.asList(newY, newX))) {
          stack.add(Arrays.asList(newY, newX));
          visited.add(Arrays.asList(newY, newX));
        }
      }
    }
    
    // Sort by Y then X if Y are equal
    perimeterBlocks.sort((a, b) -> {
      if (a.get(0) != b.get(0)) return Integer.compare(a.get(0), b.get(0));
      else return Integer.compare(a.get(1), b.get(1));
    });
    
    List<Integer> previous = perimeterBlocks.get(0);
    boolean hadBelow = hasBelow(grid, previous, group);
    boolean hadAbove = hasAbove(grid, previous, group);
    for (int i = 1; i < perimeterBlocks.size(); i++) {
      List<Integer> current = perimeterBlocks.get(i);

      if (current.get(0) != previous.get(0)) {
        sideCount++;
        previous = current;
        hadBelow = hasBelow(grid, current, group);
        hadAbove = hasAbove(grid, current, group);
        continue;
      }

      if (!hadAbove && current.get(1) - previous.get(1) != 1 && !hasAbove(grid, current, group)) { // n
        sideCount += 2;
      } else if ((!hadAbove || !hasAbove(grid, current, group)) && (current.get(1) - previous.get(1)) != 1) { // n
        sideCount++;
      } else if (!hadAbove && hasAbove(grid, current, group)) {
        sideCount++;
      } else if (hadAbove && !hasAbove(grid, current, group)) {
        sideCount++;
      }

      if (!hadBelow && current.get(1) - previous.get(1) != 1 && !hasBelow(grid, current, group)) { // n
        sideCount += 2;
      } else if ((!hadBelow || !hasBelow(grid, current, group)) && (current.get(1) - previous.get(1)) != 1) { // n
        sideCount++;
      } else if (!hadBelow && hasBelow(grid, current, group)) {
        sideCount++;
      } else if (hadBelow && !hasBelow(grid, current, group)) {
        sideCount++;
      }
      previous = current;
    }

    System.out.println(sideCount);

    // If next X == previous X continue, else add 1 to sideCount. Repeat for Y but sort again by X, Y and check for Y

    return sideCount * area;
  }

  static private boolean hasAbove(String[] grid, List<Integer> coords, char group) {
    int y = coords.get(0) - 1;
    int x = coords.get(1);
    if (y < 0 || y >= grid.length || x < 0 || x >= grid[0].length()) return false;
    return grid[y].charAt(x) == group;
  }

  static private boolean hasBelow(String[] grid, List<Integer> coords, char group) {
    int y = coords.get(0) + 1;
    int x = coords.get(1);
    if (y < 0 || y >= grid.length || x < 0 || x >= grid[0].length()) return false;
    return grid[y].charAt(x) == group;
  }
}
