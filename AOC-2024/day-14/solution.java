import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Solution {
  static private final int WIDTH = 101;
  static private final int HEIGHT = 103;
  static private final int SECONDS = 100;

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();
    String[] lines = input.split("\n");

    int midY = HEIGHT / 2;
    int midX = WIDTH / 2;
    
    long[] quadrants = new long[]{0, 0, 0, 0};
    for (String line : lines) {
      line = line.trim();
      int rx = Integer.parseInt(line.split("=")[1].split(",")[0]);
      int ry = Integer.parseInt(line.split("=")[1].split(",")[1].split(" ")[0]);
      int vx = Integer.parseInt(line.split("v=")[1].split(",")[0]);
      int vy = Integer.parseInt(line.split("v=")[1].split(",")[1]);

      int x = ((rx + (SECONDS * vx)) % WIDTH + WIDTH) % WIDTH;
      int y = ((ry + (SECONDS * vy)) % HEIGHT + HEIGHT) % HEIGHT;

      if (x == midX || y == midY) continue;
      if (x > midX && y > midY) quadrants[3]++;
      else if (x > midX && y < midY) quadrants[2]++;
      else if (x < midX && y > midY) quadrants[1]++;
      else if (x < midX && y < midY) quadrants[0]++;
    }
    
    long safetyFactor = quadrants[0] * quadrants[1] * quadrants[2] * quadrants[3];
    System.out.println(safetyFactor);
  }
}
