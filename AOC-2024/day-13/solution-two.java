import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Solution {
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();
    String[] lines = input.split("\n");
    
    long totalTokens = 0;
    for (int i = 0; i < lines.length + 1; i += 4) {
      int ax = Integer.parseInt(lines[i].substring(lines[i].indexOf('+') + 1, lines[i].indexOf(',')));
      int ay = Integer.parseInt(lines[i].substring(lines[i].lastIndexOf('+') + 1).trim());

      int bx = Integer.parseInt(lines[i+1].substring(lines[i+1].indexOf('+') + 1, lines[i+1].indexOf(',')));
      int by = Integer.parseInt(lines[i+1].substring(lines[i+1].lastIndexOf('+') + 1).trim());

      long px = Long.parseLong(lines[i+2].substring(lines[i+2].indexOf('=') + 1, lines[i+2].indexOf(','))) + 10000000000000L;
      long py = Long.parseLong(lines[i+2].substring(lines[i+2].lastIndexOf('=') + 1).trim()) + 10000000000000L;
      
      // Thanks salman0246 for telling about cramers rule lol (https://byjus.com/maths/cramers-rule/)
      int[][] A = new int[][]{ {ax, bx}, {ay, by} };
      long[] B = new long[]{ px, py };

      long D = (A[0][0] * A[1][1]) - (A[0][1] * A[1][0]);
      if (D == 0) continue;

      long dx = (B[0] * A[1][1]) - (A[0][1] * B[1]);
      long dy = (A[0][0] * B[1]) - (B[0] * A[1][0]);

      long x = dx / D;
      long y = dy / D;
      
      if (x * ax + y * bx == px && x * ay + y * by == py) totalTokens += (x * 3) + y;
    }
    System.out.println(totalTokens);
  }
}
