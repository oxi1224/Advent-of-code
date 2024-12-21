import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

class Solution {
  private static HashMap<String, int[]> keypad = new HashMap<>();
  private static HashMap<String, int[]> dpad = new HashMap<>();

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("example.txt")).trim();
    String[] lines = input.split("\n");

    keypad.put("7", new int[]{0, 0});
    keypad.put("8", new int[]{0, 1});
    keypad.put("9", new int[]{0, 2});
    keypad.put("4", new int[]{1, 0});
    keypad.put("5", new int[]{1, 1});
    keypad.put("6", new int[]{1, 2});
    keypad.put("1", new int[]{2, 0});
    keypad.put("2", new int[]{2, 1});
    keypad.put("3", new int[]{2, 2});
    keypad.put("0", new int[]{3, 1});
    keypad.put("A", new int[]{3, 2});

    dpad.put("^", new int[]{0, 1});
    dpad.put("A", new int[]{0, 2});
    dpad.put("<", new int[]{1, 0});
    dpad.put("v", new int[]{1, 1});
    dpad.put(">", new int[]{1, 2});

    long complexity = 0;
    for (String code : lines) {
      System.out.println(code);
      code = code.trim();
      String steps = "";

      int[] pos = new int[]{3, 2};
      // int[] dpadPos = new int[]{0, 2};
      for (String digit : code.split("")) {
        int[] coords = keypad.get(digit);
        int dy = coords[0] - pos[0];
        int dx = coords[1] - pos[1];

        if (pos[0] == 3 && coords[1] == 0) {
          steps += "^";
          dy += 1;
          pos[0] += dy - 1;
        } else {
          pos[0] += dy;
        }
        pos[1] += dx;
        for (int i = 0; i < Math.abs(dx); i++) steps += dx > 0 ? ">" : "<";
        for (int i = 0; i < Math.abs(dy); i++) steps += dy > 0 ? "v" : "^";
        steps += "A";
      }
      System.out.println(steps);
      System.out.println(inputChar(steps, 2));
      complexity += Integer.parseInt(code.substring(0, code.length() - 1)) * inputChar(steps, 1).length();
    }
    System.out.println(complexity);
  }

  private static String inputChar(String prevSteps, int keypad) {
    String steps = "";

    int[] pos = new int[]{0, 2};
    for (String step : prevSteps.split("")) {
      int[] coords = dpad.get(step);
      int dy = coords[0] - pos[0];
      int dx = coords[1] - pos[1];

      if (pos[0] == 0 && coords[1] == 0) {
        steps += "v";
        dy -= 1;
        pos[0] += dy + 1;
      } else {
        pos[0] += dy;
      }
      pos[1] += dx;

      for (int i = 0; i < Math.abs(dx); i++) steps += (dx > 0) ? ">" : "<";
      for (int i = 0; i < Math.abs(dy); i++) steps += (dy > 0) ? "v" : "^";
      steps += "A";
    }

    // System.out.println(steps);
    if (keypad == 2) return steps;
    else return inputChar(steps, keypad + 1);
  }
}
