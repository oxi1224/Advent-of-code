import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class Solution {
  private static class Pair {
    public final int[] key;
    public final String value;
    public Pair(int[] k, String v) {key = k; value = v;}
    public static Pair of(int[] k, String v) {return new Pair(k, v);}
  }

  private static int[][] OFFSETS = new int[][]{
    {-1, 0}, // up
    {1, 0}, // down
    {0, -1}, // left 
    {0, 1} // right
  };

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Paths.get("input.txt")).trim();
    String[] lines = input.split("\n");

    HashMap<String, int[]> keypad = new HashMap<>();
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

    HashMap<String, int[]> dpad = new HashMap<>();
    dpad.put("^", new int[]{0, 1});
    dpad.put("A", new int[]{0, 2});
    dpad.put("<", new int[]{1, 0});
    dpad.put("v", new int[]{1, 1});
    dpad.put(">", new int[]{1, 2});

    HashMap<String, List<String>> keypadPaths = new HashMap<>();
    HashMap<String, List<String>> dpadPaths = new HashMap<>();
    precomputePaths(keypadPaths, keypad, 4, 3, new int[]{3, 0});
    precomputePaths(dpadPaths, dpad, 2, 3, new int[]{0, 0});
    
    int totalComplexity = 0;
    for (String code : lines) {
      code = code.trim();
      List<String> paths = input("A", "", code, keypadPaths);
      for (int i = 1; i < 3; i++) {
        List<String> newPaths = new ArrayList<>();
        for (String path : paths) {
          newPaths.addAll(input("A", "", path, dpadPaths));
        }
        paths = newPaths;
      }

      int minLength = paths.stream().mapToInt(String::length).min().getAsInt();
      System.out.println(minLength);
      totalComplexity += Integer.parseInt(code.substring(0, code.length() - 1)) * minLength;
    }
    System.out.println(totalComplexity);
  }

  private static List<String> input(String currentPos, String currentPath, String remainingSteps, HashMap<String, List<String>> paths) {
    if (remainingSteps.isEmpty()) return Arrays.asList(currentPath);

    List<String> out = new ArrayList<>();
    String[] steps = remainingSteps.split("");
    for (String path : paths.get(currentPos + steps[0])) {
      out.addAll(input(steps[0], currentPath + path, remainingSteps.substring(1), paths));
    }
    
    return out;
  }

  private static void precomputePaths(HashMap<String, List<String>> paths, HashMap<String, int[]> pad, int rows, int cols, int[] blocked) {
    for (Map.Entry<String, int[]> startEntry : pad.entrySet()) {
      for (Map.Entry<String, int[]> endEntry : pad.entrySet()) {
        int[] start = startEntry.getValue();
        int[] end = endEntry.getValue();
        String key = startEntry.getKey() + endEntry.getKey();

        paths.putIfAbsent(key, new ArrayList<>());
        if (start[0] == end[0] && start[1] == end[1]) {
          paths.get(key).add("A");
          continue;
        }

        Queue<Pair> queue = new LinkedList<>();
        List<String> foundPaths = new ArrayList<>();
        int shortestPath = Integer.MAX_VALUE;
        
        queue.offer(Pair.of(start, ""));
        while (!queue.isEmpty()) {
          Pair p = queue.poll();

          if (p.value.length() > shortestPath) continue;
          if (p.key[0] == end[0] && p.key[1] == end[1]) {
            if (p.value.length() < shortestPath) {
              shortestPath = p.value.length();
              foundPaths.clear(); 
            }
            foundPaths.add(p.value + "A");
            continue;
          }

          for (int[] offset : OFFSETS) {
            int ny = p.key[0] + offset[0];
            int nx = p.key[1] + offset[1];
            if (ny < 0 || ny >= rows || nx < 0 || nx >= cols || (ny == blocked[0] && nx == blocked[1])) continue;
            queue.offer(Pair.of(new int[]{ny, nx}, p.value + getDirectionChar(offset)));
          }
        }

        paths.get(key).addAll(foundPaths);
      }
    }
  }

  public static String getDirectionChar(int[] offset) {
    if (offset[0] != 0) return offset[0] == -1 ? "^" : "v";
    else return offset[1] == 1 ? ">" : "<";
  }
}
