import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
  static private final int[][] OFFSETS = new int[][]{
    {0, 1}, // right
    {1, 0}, // down
    {0, -1}, // left
    {-1, 0}, // up
  };

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();

    List<List<Character>> grid = new ArrayList<>();

    String[] lines = input.split("\n");
    int rows = lines.length;
    int cols = lines[0].trim().length();

    int[] start = new int[]{0, 0};
    int[] end = new int[]{0, 0};

    for (int y = 0; y < rows; y++) {
      grid.add(new ArrayList<>());
      char[] row = lines[y].toCharArray();
      for (int x = 0; x < cols; x++) {
        grid.get(y).add(row[x]);
        if (row[x] == 'S') start = new int[]{y, x};
        else if (row[x] == 'E') end = new int[]{y, x};
      }
    }

    List<Integer> shortestPath = dfs(grid, start, end, cols, rows, true);
    System.out.println(shortestPath);
    List<List<Integer>> usedCheats = new ArrayList<>();

    int cheats = 0; 
    for (int i = 3; i < shortestPath.size(); i += 2) {
      int y = shortestPath.get(i);
      int x = shortestPath.get(i + 1);
      
      for (int[] offsetOne : OFFSETS) {
        int wallY = y + offsetOne[0];
        int wallX = x + offsetOne[1];
        if (wallY < 0 || wallY >= rows || wallX < 0 || wallX >= cols || grid.get(wallY).get(wallX) != '#') continue;
        if (usedCheats.contains(Arrays.asList(wallY, wallX))) continue;
        usedCheats.add(Arrays.asList(wallY, wallX));

        grid.get(wallY).set(wallX, '1');
        List<Integer> path = dfs(grid, start, end, cols, rows, false);
        grid.get(wallY).set(wallX, '#');
        
        if (path.get(2) >= shortestPath.get(2)) continue;
        int diff = shortestPath.get(2) - path.get(2);

        if (diff >= 100) cheats++;
        
        grid.get(wallY).set(wallX, '#');
      }
    }

    System.out.println(cheats);
  }

  private static List<Integer> dfs(List<List<Character>> grid, int[] start, int[] end, int cols, int rows, boolean collectPath) {
    // First 3 values are: y, x, steps then after that its the path taken
    PriorityQueue<List<Integer>> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.get(2), b.get(2)));
    boolean[][] visited = new boolean[rows][cols];
    pq.add(Arrays.asList(start[0], start[1], 0));

    while (!pq.isEmpty()) {
      List<Integer> cur = pq.poll();
      int y = cur.get(0), x = cur.get(1);

      if (visited[y][x]) continue;
      visited[y][x] = true;

      if (y == end[0] && x == end[1]) return cur;

      for (int[] offset : OFFSETS) {
        int ny = y + offset[0];
        int nx = x + offset[1];
        if (ny >= 0 && ny < rows && nx >= 0 && nx < cols && grid.get(ny).get(nx) != '#') {
          List<Integer> next = new ArrayList<>(cur);
          next.set(0, ny);
          next.set(1, nx);
          next.set(2, cur.get(2) + 1);
          if (collectPath) {
            next.add(ny);
            next.add(nx);
          }
          pq.add(next);
        }
      }
    }
    return Arrays.asList(-1, -1, -1);
  }
}
