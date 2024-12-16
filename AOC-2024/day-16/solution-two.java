import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
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
    String input = Files.readString(Paths.get("example-two.txt")).trim();
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
    PriorityQueue<Path> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.score));
    int minScore = Integer.MAX_VALUE;
    pq.add(new Path(start[0], start[1], 0, 0, new ArrayList<>()));
    HashSet<List<Integer>> uniqueSpaces = new HashSet<>();

    while (!pq.isEmpty()) {
      Path p = pq.poll();

      if (p.y == end[0] && p.x == end[1]) {
        if (minScore == Integer.MAX_VALUE) minScore = p.score;
        System.out.println(p.score);
        uniqueSpaces.addAll(p.visited);
        continue;
      }
      
      if (p.score > minScore) continue;
      if (p.visited.contains(Arrays.asList(p.y, p.x))) continue;

      int newY = p.y + OFFSETS[p.direction][0];
      int newX = p.x + OFFSETS[p.direction][1];

      if (newY >= 0 && newY < rows && newX >= 0 && newX < cols && grid.get(newY).get(newY) != '#') {
        p.visited.add(Arrays.asList(newY, newX));
        pq.add(new Path(newY, newX, p.direction, p.score + 1, new ArrayList<>(p.visited)));
      }
      
      // Try rotating right
      pq.add(new Path(p.y, p.x, (p.direction + 1) % 4, p.score + 1000, new ArrayList<>(p.visited)));
      // Try rotating left
      pq.add(new Path(p.y, p.x, (p.direction + 3) % 4, p.score + 1000, new ArrayList<>(p.visited)));
    }
    System.out.println(uniqueSpaces.size());
  }
}

class Path {
  public int y = 0;
  public int x = 0;
  public int direction = 0;
  public int score = 0;
  public ArrayList<List<Integer>> visited = new ArrayList<>();

  public Path(int y, int x, int direction, int score, ArrayList<List<Integer>> visited) {
    this.y = y;
    this.x = x;
    this.direction = direction;
    this.score = score;
    this.visited = visited;
  }
}

