import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

class Solution {
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt"));
    String[] grid = input.split("\n");
    int rows = grid.length;
    for (int i = 0; i < rows; i++) grid[i] = grid[i].trim(); // Get rid of newlines;
    int cols = grid[0].length();

    // e.g 0 -> [[y, x], [y, x]]
    HashMap<Character, ArrayList<List<Integer>>> antennas = new HashMap<>();
    HashSet<List<Integer>> antinodes = new HashSet<>();
    
    for (int y = 0; y < rows; y++) { 
      for (int x = 0; x < cols; x++) {
        if (grid[y].charAt(x) == '.') continue;
        antennas.putIfAbsent(grid[y].charAt(x), new ArrayList<>());
        antennas.get(grid[y].charAt(x)).add(Arrays.asList(x, y));
      }
    }

    for (Map.Entry<Character, ArrayList<List<Integer>>> entry : antennas.entrySet()) {
      for (List<Integer> currentCoords: entry.getValue()) {
        for (List<Integer> nextCoords : entry.getValue()) {
          if (currentCoords.equals(nextCoords)) continue;
          int dy = nextCoords.get(0) - currentCoords.get(0);
          int dx = nextCoords.get(1) - currentCoords.get(1);
          int antinodeY = nextCoords.get(0) + dy;
          int antinodeX = nextCoords.get(1) + dx;
          if (antinodeY < 0 || antinodeY >= rows || antinodeX < 0 || antinodeX >= cols) continue;
          antinodes.add(Arrays.asList(antinodeY, antinodeX));
        }
      }
    }

    System.out.println(antinodes.size());
  }
}
