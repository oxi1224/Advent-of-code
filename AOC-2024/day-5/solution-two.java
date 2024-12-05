import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

class Solution {
  private static HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt"));
    String[] lines = input.split("\n");
    
    int i = 0;
    String line = lines[i].trim();
    // Only fill in the map (just \n = separator between rules and orders)
    while (!line.isBlank()) {
      int left = Integer.parseInt(line.substring(0, line.indexOf('|')));
      int right = Integer.parseInt(line.substring(line.indexOf('|') + 1));
      map.putIfAbsent(left, new ArrayList<Integer>());
      map.get(left).add(right);
      i++;
      line = lines[i].trim();
    }
    line = lines[i++];

    int midpointSum = 0;
    for (;i < lines.length; i++) {
      line = lines[i];
      String[] split = line.trim().split(",");
      int[] order = new int[split.length];
      for (int j = 0; j < split.length; j++) order[j] = Integer.parseInt(split[j]);
      
      int[] result = isOrdered(order);
      if (result[0] == 1) continue;
      
      while (result[0] != 1) {
        int temp = order[result[2]];
        order[result[2]] = order[result[1]];
        order[result[1]] = temp;
        result = isOrdered(order);
      }
      midpointSum += order[(int)Math.ceil(order.length / 2)];
    }
    System.out.println(midpointSum);
  }
  
  // If is not ordered correctly returns [0, i, j] otherwise [1, 0, 0]
  static private int[] isOrdered(int[] order) {
    for (int i = 0; i < order.length; i++) {
      int cur = order[i];
      if (!map.containsKey(cur)) continue;
      for (int j = 0; j < order.length; j++) {
        if (j == i) continue;
        if (
          (j > i && !map.get(cur).contains(order[j])) ||
          (j < i && map.get(cur).contains(order[j]))
        ) {
          return new int[]{0, i, j};
        }
      }
    }
    return new int[]{1, 0, 0};
  }
}
