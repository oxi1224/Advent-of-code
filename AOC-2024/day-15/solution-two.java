import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

class Solution {
  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();
    String[] lines = input.split("\n");

    ArrayList<ArrayList<Character>> grid = new ArrayList<>();
    ArrayList<Character> moves = new ArrayList<>();

    boolean readingMoves = false;
    int[] robot = new int[]{0, 0};

    for (int i = 0; i < lines.length; i++) {
      if (lines[i].trim().isBlank()) {
        readingMoves = true;
        continue;
      }

      if (!readingMoves) {
        grid.add(new ArrayList<>());
        char[] row = lines[i].trim().toCharArray();
        for (int j = 0; j < row.length; j++) {
          char c = row[j];
          if (c == '#' || c == '.') {
            grid.get(i).add(c);
            grid.get(i).add(c);
          } else if (c == 'O') {
            grid.get(i).add('[');
            grid.get(i).add(']');
          } else if (c == '@') {
            grid.get(i).add('@');
            grid.get(i).add('.');
            robot = new int[]{i, j * 2};
          }
        }
        continue;
      }

      for (char c : lines[i].trim().toCharArray()) moves.add(c);
    }

    for (char move : moves) {
      int[] offset = getOffset(move);
      int y = robot[0] + offset[0];
      int x = robot[1] + offset[1];

      if (grid.get(y).get(x) == '#') {
        continue;
      } else if (grid.get(y).get(x) == '.') {
        grid.get(robot[0]).set(robot[1], '.');
        grid.get(y).set(x, '@');
        robot[0] = y;
        robot[1] = x;
      } else if (isBox(grid, y, x)) {
        // If moving left/right
        if (offset[0] == 0) {
          // Count how many characters are to the left/right
          int span = 1;
          int boxY = y + offset[0];
          int boxX = x + offset[1];
          while (isBox(grid, boxY, boxX)) {
            span++;
            boxY += offset[0];
            boxX += offset[1];
          }
          
          // element at y + offset * span is the one AFTER last box
          if (grid.get(y + offset[0] * span).get(x + offset[1] * span) == '#') continue;
          
          // move every box by offset, check for direction is required cause boxes start with [ when going right and ] when going left 
          for (int box = 1; box <= span; box += 2) {
             grid.get(y).set(x + offset[1] * box, offset[1] == -1 ? ']' : '[');
             grid.get(y).set(x + offset[1] * (box + 1), offset[1] == -1 ? '[' : ']' );
          }
          
          // Update robot position and grid
          grid.get(robot[0]).set(robot[1], '.');
          grid.get(y).set(x, '@');
          robot[0] = y;
          robot[1] = x;
          
        // If moving up/down
        } else if (offset[1] == 0) {
          // In order: [min y, min x], [max y, max x], used for determinig the moving boxes
          int[][] colissionBox = new int[][]{ {9999, 9999}, {0, 0} };
          
          // DFS to determine the collision box
          Stack<int[]> stack = new Stack<>();
          stack.push(new int[]{y, x});
          stack.push(new int[]{y, grid.get(y).get(x) == '[' ? x + 1 : x - 1}); // Push the other brace
          while (!stack.empty()) {
            int[] coords = stack.pop();
            // current Y < minimum Y
            if (coords[0] < colissionBox[0][0]) colissionBox[0][0] = coords[0];
            // current X < minimum X
            if (coords[1] < colissionBox[0][1]) colissionBox[0][1] = coords[1];
            // current Y > maximum Y
            if (coords[0] > colissionBox[1][0]) colissionBox[1][0] = coords[0];
            // current X < maximum X
            if (coords[1] > colissionBox[1][1]) colissionBox[1][1] = coords[1];
            
            int boxY = coords[0] + offset[0];
            int boxX = coords[1];
            if (!isBox(grid, boxY, boxX)) continue;

            stack.push(new int[]{ boxY, boxX });
            stack.push(new int[]{ boxY, grid.get(boxY).get(boxX) == '[' ? boxX + 1 : boxX - 1 }); // Push the other brace
          }
          
          // Pieces which should be moved
          ArrayList<List<Integer>> pieces = new ArrayList<>();
          for (int boxY = colissionBox[0][0]; boxY <= colissionBox[1][0]; boxY++) {
            for (int boxX = colissionBox[0][1]; boxX <= colissionBox[1][1]; boxX++) {
              // if (
              //   boxY != colissionBox[0][0] &&
              //   grid.get(boxY).get(boxX) == '[' &&
              //   !isBox(grid, boxY + offset[0], boxX) &&
              //   !isBox(grid, boxY + offset[0], boxX + 1)
              // ) continue;
              // Check if box is fully inside of collision area
              if (
                grid.get(boxY).get(boxX) == '[' &&
                grid.get(boxY).get(boxX + 1) == ']' && 
                (boxX + 1) <= colissionBox[1][1]
              ) pieces.add(Arrays.asList(boxY, boxX + 1));
            }
          }
          
          // Check if there arent any walls outside of the bounding box that would block movemenet
          boolean blocked = false;
          for (int boxY = colissionBox[0][0]; boxY <= colissionBox[1][0]; boxY++) {
            for (int boxX = colissionBox[0][1]; boxX <= colissionBox[1][1]; boxX++) {
              if (
                pieces.contains(Arrays.asList(boxY, boxX + 1)) &&
                (grid.get(boxY + offset[0]).get(boxX) == '#' ||
                grid.get(boxY + offset[0]).get(boxX + 1) == '#')
              ) blocked = true;
            }
          }
          // for (int boxX = colissionBox[0][1]; boxX <= colissionBox[1][1]; boxX++) {
          //   // Going up checks Y > min and down Y > max
          //   int collisionY = offset[0] == -1 ? colissionBox[0][0] : colissionBox[1][0];
          //   if (grid.get(collisionY + offset[0]).get(boxX) == '#') {
          //     blocked = true;
          //     break;
          //   }
          // }
          // If movement is blocked
          if (blocked) continue;
          
          // Easier to reverse the order when going up so that next boxes dont override previous ones
          if (offset[0] == 1) Collections.reverse(pieces);
          // Update positions and remove previous
          for (List<Integer> piece : pieces) {
            grid.get(piece.get(0) + offset[0]).set(piece.get(1) - 1, '[');
            grid.get(piece.get(0) + offset[0]).set(piece.get(1), ']');
            grid.get(piece.get(0)).set(piece.get(1) - 1, '.');
            grid.get(piece.get(0)).set(piece.get(1), '.');
          }
        
          // Update robot position
          grid.get(robot[0]).set(robot[1], '.');
          grid.get(y).set(x, '@');
          robot[0] = y;
          robot[1] = x;
        }
      }
    }
    
    // Print finished grid
    for (ArrayList<Character> row : grid) {
      for (char c : row) {
        System.out.print(c);
      }
      System.out.println();
    }

    long GPSsum = 0;
    for (int y = 0; y < grid.size(); y++) {
      for (int x = 0; x < grid.get(0).size(); x++) {
        if (grid.get(y).get(x) == '[') GPSsum += 100L * y + x;
      }
    }
    System.out.println(GPSsum);
  }

  private static int[] getOffset(char c) {
    if (c == '^') return new int[]{-1, 0};
    if (c == 'v') return new int[]{1, 0};
    if (c == '<') return new int[]{0, -1};
    if (c == '>') return new int[]{0, 1};
    return new int[]{-1, -1};
  }

  private static boolean isBox(ArrayList<ArrayList<Character>> grid, int y, int x) {
    char c = grid.get(y).get(x);
    return c == '[' || c == ']';
  }
}
