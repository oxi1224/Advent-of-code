import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

class Solution {
  static private final int[][] OFFSETS = new int[][]{
    {0, 1}, // right
    {1, 0}, // down
    {0, -1}, // left
    {-1, 0}, // up
  };

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();
    String[] lines = input.split("\n");
    int rows = lines.length;
    int cols = lines[0].trim().length();

    ArrayList<ArrayList<Character>> grid = new ArrayList<>();
    int[] start = new int[2];
    int[] end = new int[2];
    for (int i = 0; i < rows; i++) {
      char[] chars = lines[i].trim().toCharArray();
      grid.add(new ArrayList<>());
      for (int j = 0; j < cols; j++) {
        grid.get(i).add(chars[j]);
        if (chars[j] == 'S') {
          start[0] = i;
          start[1] = j;
        } else if (chars[j] == 'E') {
          end[0] = i;
          end[1] = j;
        }
      }
    }
    // int[] = [y, x, direction, cost] 
    PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[3])); // Order the priority queue by cost
    // String = "y,x,direction"
    Set<String> visited = new HashSet<>();
    pq.add(new int[]{start[0], start[1], 0, 0});

    while (!pq.isEmpty()) {
      int[] cur = pq.poll();
      int y = cur[0], x = cur[1];
      int dir = cur[2], cost = cur[3];

      if (y == end[0] && x == end[1]) {
        System.out.println(cost);
        return;
      }

      String key = y + "," + x + "," + dir;
      if (visited.contains(key)) continue;
      visited.add(key);

      int ny = y + OFFSETS[dir][0];
      int nx = x + OFFSETS[dir][1];

      if (ny >= 0 && ny < rows && nx >= 0 && nx < cols && grid.get(ny).get(nx) != '#') {
        pq.add(new int[]{ny, nx, dir, cost + 1});
      }
      
      // Try rotating right
      pq.add(new int[]{y, x, (dir + 1) % 4, cost + 1000});
      // Try rotating left
      pq.add(new int[]{y, x, (dir + 3) % 4, cost + 1000});
    }
    System.out.println("No path found");
  }
}
