import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class Solution {
  static private final int WIDTH = 101;
  static private final int HEIGHT = 103;
  static private final int SECONDS = 10000;

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();
    String[] lines = input.split("\n");

    ArrayList<int[][]> robots = new ArrayList<>();
    
    for (String line : lines) {
      line = line.trim();
      int rx = Integer.parseInt(line.split("=")[1].split(",")[0]);
      int ry = Integer.parseInt(line.split("=")[1].split(",")[1].split(" ")[0]);
      int vx = Integer.parseInt(line.split("v=")[1].split(",")[0]);
      int vy = Integer.parseInt(line.split("v=")[1].split(",")[1]);

      robots.add(new int[][]{
        {rx, ry},
        {vx, vy}
      });
    }
    File f = new File("output.txt");
    PrintWriter pw = new PrintWriter(f);
    for (int i = 0; i < SECONDS; i++) {
      for (int[][] robot : robots) {
        robot[0][0] = ((robot[0][0] + robot[1][0]) % WIDTH + WIDTH) % WIDTH;
        robot[0][1] = ((robot[0][1] + robot[1][1]) % HEIGHT + HEIGHT) % HEIGHT;
      }
      StringBuilder sb = new StringBuilder();
      for (int y = 0; y < HEIGHT; y++) {
        for (int x = 0; x < WIDTH; x++) {
          if (isRobot(robots, x, y)) sb.append("#");
          else sb.append(".");
        }
        sb.append("\n");
      }
      pw.printf("%d\n", i + 1);
      pw.print(sb.toString());
      System.out.printf("Writing second %d\n", i + 1);
    }
    pw.close();
  }

  private static boolean isRobot(ArrayList<int[][]> robots, int x, int y) {
    for (int[][] robot : robots) {
      if (robot[0][0] == x && robot[0][1] == y) return true;
    }
    return false;
  }
}
