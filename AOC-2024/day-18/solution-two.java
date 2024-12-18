import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.PriorityQueue;

class Solution {
  static private final int[][] OFFSETS = new int[][]{
    {0, 1}, // right
    {1, 0}, // down
    {0, -1}, // left
    {-1, 0}, // up
  };

  static private final int COLS = 71;
  static private final int ROWS = 71;
  static private final int END_X = ROWS - 1;
  static private final int END_Y = COLS - 1;
  static private final int TO_FALL = 1024;

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();

    String[] lines = input.split("\n");
    ArrayList<int[]> bytes = new ArrayList<>();

    for (String l : lines) {
      String[] split = l.trim().split(",");
      bytes.add(new int[]{Integer.parseInt(split[0].trim()), Integer.parseInt(split[1].trim())});
    }

    ArrayList<ArrayList<Character>> grid = new ArrayList<>();
    for (int y = 0; y < ROWS; y++) {
      grid.add(new ArrayList<>());
      for (int x = 0; x < COLS; x++) {
        grid.get(y).add('.');
      }
    }

    for (int i = 0; i < TO_FALL; i++) grid.get(bytes.get(i)[1]).set(bytes.get(i)[0], '#');

    int current_block = TO_FALL;
    while (dijkstra(grid) != -1) {
      int[] coords = bytes.get(current_block);
      grid.get(coords[1]).set(coords[0], '#');
      current_block++;
    }
    System.out.printf("%d,%d\n", bytes.get(current_block - 1)[0], bytes.get(current_block - 1)[1]);
  }

  private static int dijkstra(ArrayList<ArrayList<Character>> grid) {
    // int[] = [y, x, steps] 
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[2], b[2])); // Order the priority queue by distance ascending 
    boolean[][] visited = new boolean[COLS][ROWS];
    pq.add(new int[]{0, 0, 0});

    while (!pq.isEmpty()) {
      int[] cur = pq.poll();
      int y = cur[0], x = cur[1];
      int steps = cur[2];

      if (visited[y][x]) continue;
      visited[y][x] = true;

      if (y == END_Y && x == END_X) {
        return steps;
      }

      for (int[] offset : OFFSETS) {
        int ny = y + offset[0];
        int nx = x + offset[1];
        if (ny >= 0 && ny < ROWS && nx >= 0 && nx < COLS && grid.get(ny).get(nx) != '#') {
          pq.add(new int[]{ny, nx, steps + 1});
        }
      }
    }
    return -1;
  }
}
